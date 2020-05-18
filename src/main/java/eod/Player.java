package eod;

import eod.IO.Input;
import eod.IO.Output;
import eod.card.abstraction.Card;
import eod.card.collection.Deck;
import eod.card.collection.Hand;
import eod.event.ObjectDeadEvent;
import eod.event.ObjectMovingEvent;
import eod.exceptions.GameLosingException;
import eod.snapshots.Snapshotted;
import eod.warObject.Damageable;
import eod.warObject.WarObject;
import eod.warObject.leader.Leader;

import java.awt.*;
import java.util.*;

public class Player implements Snapshotted<Player.Snapshot>,
                                GameObject {

    private Deck deck;
    private Game game;
    private Hand hand;
    private Leader leader;
    private Input input;
    private Output output;
    private String name;
    private boolean isPlayerA;

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

    public void handReceive(ArrayList<Card> h) {
        hand.receive(h);
    }

    public void drawFromDeck(int count) {
        Card[] cards = deck.draw(count);
        handReceive(new ArrayList<>(Arrays.asList(cards)));
    }

    public boolean checkInHand(Class<? extends Card> c) {
        return hand.containsType(c);
    }

    public void dropCards() {
        hand.dropAllCards();
    }

    public void dropCards(int k) throws IllegalArgumentException {
        hand.randomlyDropCards(k);
    }
    
    public void announceWon() {
        
    }
    
    public void announceLost() {
        
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
        //TODO: asks the player to select a character
        Random random = new Random();
        return objects[random.nextInt(objects.length)];
    }

    public Point selectPosition(ArrayList<Point> points) {
        //TODO: connection with the frontend
        Random random = new Random();
        return points.get(random.nextInt(points.size()));
    }

    public <T extends Card> T selectCard(T[] cards) {
        //TODO:connection with the frontend
        Random random = new Random();
        return cards[random.nextInt(cards.length)];
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
