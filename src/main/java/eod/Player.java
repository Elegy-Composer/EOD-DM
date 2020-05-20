package eod;

import eod.IO.Input;
import eod.IO.Output;
import eod.card.abstraction.Card;
import eod.card.collection.Deck;
import eod.card.collection.Hand;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.effect.Summon;
import eod.event.Event;
import eod.event.*;
import eod.event.listener.EventListener;
import eod.exceptions.GameLosingException;
import eod.exceptions.NotSupportedException;
import eod.param.AttackParam;
import eod.snapshots.Snapshotted;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;
import eod.warObject.leader.Leader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Player implements Snapshotted<Player.Snapshot>,
                                GameObject, EventListener, EffectExecutor {

    private Deck deck;
    private Game game;
    private Hand hand;
    private Leader leader;
    private Input input;
    private Output output;
    private String name;
    private boolean isPlayerA;
    private boolean isActingPlayer = false;

    public Player(Deck deck, String name) {
        this(deck, new Hand(), name);
    }

    public Player(Deck deck, Hand hand, String name) {
        this.deck = deck;
        this.hand = hand;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void attachToGame(Game game) {
        this.game = game;
        game.registerListener(this);
    }
    public void attachIO(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void setPlayerA(boolean is) {
        isPlayerA = is;
    }

    public boolean isPlayerA() {
        return isPlayerA;
    }

    public void setIsActing(boolean acting) {
        isActingPlayer = acting;
    }

    public void handReceive(ArrayList<Card> h) {
        hand.receive(h);
        output.sendReceivedCards(this, h.toArray(new Card[0]));
    }

    public void drawFromDeck(int count) {
        output.sendDrawingCards(this);

        Card[] cards = deck.draw(count);
        handReceive(new ArrayList<>(Arrays.asList(cards)));

        output.sendReceivedCards(this, cards);
    }

    public boolean checkCardTypeInHand(Class<? extends Card> c) {
        return hand.containsType(c);
    }

    public void dropCards() {
        hand.dropAllCards();
    }

    public void dropCards(int k) throws IllegalArgumentException {
        hand.randomlyDropCards(k);
    }

    public void startAutoAttackInOrder() {
        Gameboard board = game.getBoard();
        ArrayList<Character> characters;
        if(game.isPlayerA(this)) {
            characters = collectCharacterFromAView(board);
        } else {
            characters = collectCharacterFromBView(board);
        }
        //TODO: start auto attack
        output.sendRoundStartEffectActivate();
    }

    public void enterActionPhase(EffectExecutor nextHandler) {
        while(true) {
            Card c = input.waitForPlayCard(hand.toArray());
            //TODO: add {or cost == 0}
            if(c == null) {
                break;
            }
            if (hand.containsCard(c)) {
                playCard(c, nextHandler);
            }
        }
    }

    private EffectExecutor effectNextHandler;
    public void playCard(Card c, EffectExecutor nextHandler) {
        //TODO: cost handling
        //c.effect();
        effectNextHandler = nextHandler;
        c.effect(this);
    }

    @Override
    public void tryToExecute(Effect effect) {
        Summon.HandlerType desired = effect.desiredHandlerType();
        if(desired == Summon.HandlerType.Owner && isActingPlayer) {
            effect.action(this);
        } else if(desired == Summon.HandlerType.Rival && !isActingPlayer) {
            effect.action(this);
        } else if(effectNextHandler != null) {
            effectNextHandler.tryToExecute(effect);
        }
    }

    private ArrayList<Character> collectCharacterFromAView(Gameboard board) {
        ArrayList<Character> characters = new ArrayList<>();
        for(int y = Gameboard.boardSize-1; y >= 0; y--) {
            for(int x = 0; x < Gameboard.boardSize; x++) {
                Character character = board.getObjectOn(x, y);
                if(character.getPlayer().equals(this)) {
                    characters.add(character);
                }
            }
        }
        return characters;
    }

    private ArrayList<Character> collectCharacterFromBView(Gameboard board) {
        ArrayList<Character> characters = new ArrayList<>();
        for(int y = 0; y < Gameboard.boardSize; y++) {
            for(int x = Gameboard.boardSize-1; x >= 0; x--) {
                Character character = board.getObjectOn(x, y);
                if(character.getPlayer().equals(this)) {
                    characters.add(character);
                }
            }
        }
        return characters;
    }

    public void announceWon() {
        output.sendWinning(this);
    }
    
    public void announceLost() {
        output.sendLosing(this);
    }

    //TODO: implement validateDeck, just by checking the number of cards and other things.
    public boolean validateDeck() {
        return true;
    }

    public Leader getLeader() {
        return leader;
    }

    public Gameboard getBoard() {
        return game.getBoard();
    }

    public boolean isLeaderAlive() {
        return leader.isAlive();
    }

    public boolean inBase(Point p) {
        return game.getBoard().inBase(this, p);
    }

    public ArrayList<Damageable> damage(CanAttack attacker,
                                        Damageable target,
                                        AttackParam param) throws NotSupportedException {
        return attacker.attack(target, param);
    }

    public ArrayList<Damageable> damage(CanAttack attack,
                                       ArrayList<Point> targets,
                                       AttackParam param) throws NotSupportedException {
        return attack.attack(targets, param);
    }

    public void heal(Damageable damageable, int hp) {
        WarObject object = (WarObject) damageable;
        if(object.getPlayer().equals(this)) {
            damageable.heal(hp);
        }
    }

    public void move(WarObject target, int step) {
        target.move(step);
    }

    public void sendPlayerOrder(boolean isFirst) {
        output.sendPlayerOrder(this, isFirst);
    }

    public ArrayList<Point> getBaseEmpty() {
        Gameboard gameboard = game.getBoard();
        int boardEdge = Gameboard.boardSize-1;
        if(game.isPlayerA(this)) {
            return gameboard.allEmptySpaces(new Point(0, 0));
        }
        else {
            return gameboard.allEmptySpaces(new Point(boardEdge, boardEdge));
        }
    }

    public ArrayList<Point> getBase() {
        Gameboard gameboard = game.getBoard();
        int boardEdge = Gameboard.boardSize - 1;
        if(game.isPlayerA(this)) {
            return gameboard.allSpaces(new Point(0,0));
        } else {
            return gameboard.allSpaces(new Point(boardEdge, boardEdge));
        }
    }

    public ArrayList<Point> getConflictEmpty() {
        return game.getBoard().allEmptySpaces(new Point(Gameboard.firstLine, Gameboard.firstLine));
    }

    public void summonObject(WarObject object, Point point) {
        getBoard().summonObject(object, point);
        output.sendWarObjectSummoned(object, point);
    }

    @Override
    public void teardown() {
        hand.teardown();
        hand = null;
        deck.teardown();
        deck = null;
        leader.teardown();
        leader = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(deck, player.deck) &&
                Objects.equals(hand, player.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck, hand);
    }

    @Override
    public Snapshot takeSnapshot() {
        return new Snapshot();
    }

    public Player rival() {
        return game.getRivalPlayer(this);
    }

    public WarObject selectObject(WarObject[] objects) {
        return input.waitForChooseWarObjectFrom(objects);
    }

    public WarObject[] selectMultipleObject(WarObject[] objects, int number) {
        return input.waitForChooseWarObjectFrom(objects, number);
    }

    public Point selectPosition(ArrayList<Point> points) {
        return input.waitForChoosePointFrom(points.toArray(new Point[0]));
    }

    public Card selectCard(Card[] cards) {
        return input.waitForChooseCardFrom(cards);
    }

    public void moveObject(WarObject object, Point point) {
        game.getBoard().moveObject(object.position, point);
        game.sendEvent(this, new ObjectMovingEvent(object, point));
        object.updatePosition(point);
    }

    public void loseObject(WarObject object) {
        getBoard().removeObject(object);
        if(object instanceof Damageable) {
            game.sendEvent(this, new ObjectDeadEvent((Damageable) object));
        }
    }

    public void loseLeader() throws GameLosingException {
        throw new GameLosingException("Player "+this+" loses.");
    }

    public ArrayList<Point> getFL(Point p, int num) {
        int dx, dy;
        if(isPlayerA()) {
            dx = 1;
            dy = 1;
        } else {
            dx = -1;
            dy = -1;
        }
        return getBoard().getLine(p, dx, dy, num);
    }

    public ArrayList<Point> getFR(Point p, int num) {
        int dx, dy;
        if(isPlayerA()) {
            dx = 1;
            dy = -1;
        } else {
            dx = -1;
            dy = 1;
        }
        return getBoard().getLine(p, dx, dy, num);
    }

    public ArrayList<Point> getFront(Point p, int num) {
        int dx;
        if(isPlayerA()) {
            dx = 1;
        } else {
            dx = -1;
        }
        return getBoard().getLine(p, dx, 0, num);
    }

    @Override
    public ArrayList<Class<? extends Event>> supportedEventTypes() {
        //TODO: add RegionalAttackEvent support
        return new ArrayList<Class<? extends Event>>(){{
            add(RoundStartEvent.class);
            add(RoundEndEvent.class);
            add(DirectAttackEvent.class);
            add(ObjectDeadEvent.class);
            add(ObjectMovingEvent.class);
        }};
    }

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
        if(event instanceof RoundStartEvent) {
            output.sendRoundStarted(((RoundStartEvent) event).getStartedRound());
            return;
        }
        if(event instanceof RoundEndEvent) {
            output.sendRoundEnded(((RoundEndEvent) event).getEndedRound());
            return;
        }
        if(event instanceof DirectAttackEvent) {
            DirectAttackEvent directAttack = (DirectAttackEvent) event;
            Character attacker = directAttack.getAttacker();
            for(Character victim: directAttack.getTargets()) {
                output.sendCharacterAttacked(attacker, victim);
            }
            return;
        }
        if(event instanceof ObjectDeadEvent) {
            ObjectDeadEvent objectDeadEvent = (ObjectDeadEvent) event;
            WarObject deadObject = (WarObject) objectDeadEvent.getDeadObject();
            output.sendWarObjectDied(deadObject);
            return;
        }
        if(event instanceof ObjectMovingEvent) {
            ObjectMovingEvent movingEvent = (ObjectMovingEvent) event;
            output.sendWarObjectMoved(movingEvent.getObject(), movingEvent.getNewPos());
        }
    }

    public class Snapshot implements eod.snapshots.Snapshot {
        private Deck.Snapshot deckSnapshot = deck.takeSnapshot();
        private Hand.Snapshot handSnapshot = hand.takeSnapshot();

        public Deck.Snapshot getDeck() {
            return deckSnapshot;
        }

        public Hand.Snapshot getHand() {
            return handSnapshot;
        }
    }
}
