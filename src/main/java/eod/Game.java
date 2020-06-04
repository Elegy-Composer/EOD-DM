package eod;

import eod.card.abstraction.Card;
import eod.card.abstraction.action.ActionCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.event.AttackEvent;
import eod.event.Event;
import eod.event.relay.EventManager;
import eod.event.RoundEndEvent;
import eod.event.RoundStartEvent;
import eod.event.relay.EventReceiver;
import eod.exceptions.GameLosingException;
import eod.exceptions.NotSupportedException;
import eod.param.AttackParam;
import eod.snapshots.Snapshotted;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

//represent a game instance
//each manages a ongoing game
public class Game implements Snapshotted<Game.Snapshot>, GameObject, EffectExecutor {

    private Player A;
    private Player B;
    private Gameboard gameboard = new Gameboard();
    private Player[] playerOrder;
    private EventManager eventManager = new EventManager();
    private Round currentRound;
    private LinkedHashMap<Round, Snapshot> history = new LinkedHashMap<>();
    private int maxHistoryLength = 7;

    public Game(Player A, Player B) {
        this.A = A;
        this.B = B;

        A.attachToGame(this);
        B.attachToGame(this);
        A.setPlayerA(true);
        B.setPlayerA(false);
    }


    public void start() {
        A.validateDeck();
        B.validateDeck();

        playerOrder = decidePlayerOrder();

        playerOrder[0].sendPlayerOrder(true);
        playerOrder[1].sendPlayerOrder(false);

        A.drawFromDeck(3);
        B.drawFromDeck(3);

        //TODO: rewrite, the imagineer changed the rule.
        boolean hasInvalid;
        int drawRound = 0;
        do {
            hasInvalid = false;
            for(int i = 0;i < playerOrder.length;i++) {
                Player player = playerOrder[i];
                Player other = playerOrder[i==0?1:0];

                if(handIsInvalid(player)) {
                    hasInvalid = true;
                    other.drawFromDeck(1);
                    player.dropCards();
                    player.drawFromDeck(3);
                }
            }
            if(hasInvalid) {
                drawRound++;
            }
            if(!hasInvalid) {
                break;
            } else if(drawRound >= 3) {
                break;
            }
        } while(true);


        // the second player should receive a {placeholder} at the start
        ArrayList<Card> firstHand = new ArrayList<>();
        // TODO: add the first card of the second player
        playerOrder[1].handReceive(firstHand);

        currentRound = new Round(playerOrder[0], 1);
        playerOrder[0].setIsActing(true);
        while(true) {
            try {
                eventManager.send(this, new RoundStartEvent(currentRound));

                gameLoop(currentRound.getPlayer());

                eventManager.send(this, new RoundEndEvent(currentRound));
                history.put(currentRound, takeSnapshot());

                currentRound.getPlayer().setIsActing(false);
                currentRound = changeRound(currentRound);
                currentRound.getPlayer().setIsActing(true);

                if(history.size() > maxHistoryLength) {
                    Round first = history.keySet().toArray(new Round[0])[0];
                    history.remove(first);
                }

            } catch (GameLosingException e) {
                break;
            }
        }

        // TODO: things to do when a person wins
        if(A.isLeaderAlive()) {
            A.announceWon();
            B.announceLost();
        } else if(B.isLeaderAlive()) {
            B.announceWon();
            A.announceLost();
        }

        teardown();
    }

    private Player[] decidePlayerOrder() {
        Random random = new Random();
        boolean AFirst = random.nextBoolean();
        if(AFirst) {
            return new Player[]{A, B};
        } else {
            return new Player[]{B, A};
        }
    }

    private Round changeRound(Round current) {
        int currentNumber = current.getNumber();
        if(current.getPlayer().isPlayerA() == playerOrder[0].isPlayerA()) {
            return new Round(playerOrder[1], currentNumber);
        } else {
            return new Round(playerOrder[0], currentNumber + 1);
        }
    }

    public Player getRivalPlayer(Player player) {
        if(player.equals(A)) {
            return B;
        } else {
            return A;
        }
    }

    public boolean isPlayerA(Player unknown) {
        return unknown.equals(A);
    }

    private boolean handIsInvalid(Player player) {
        return !player.checkCardTypeInHand(ActionCard.class);
    }

    private void gameLoop(Player player) throws GameLosingException {
        player.drawFromDeck(currentRound.getNumber() == 1 ? 2:1);
        player.startAutoAttackInOrder();
        player.enterActionPhase(this);
    }

    @Override
    public void tryToExecute(Effect effect) {
        if(effect.desiredHandlerType() == Effect.HandlerType.Game) {
            effect.action(this);
        } else {
            Player rival = getRivalPlayer(currentRound.getPlayer());
            rival.tryToExecute(effect);
        }
    }

    public ArrayList<Damageable> damage(CanAttack attacker,
                                        ArrayList<Point> targets,
                                        AttackParam param) throws NotSupportedException {
        return attacker.attack(gameboard, targets, param);
    }

    public ArrayList<Damageable> damage(CanAttack attacker,
                                        Point target,
                                        AttackParam param) throws NotSupportedException {
        ArrayList<Point> targets = new ArrayList<Point>() {{
            add(target);
        }};
        return damage(attacker, targets, param);
    }

    public void sendEvent(GameObject sender, Event event) {
        eventManager.send(sender, event);
    }

    public void registerReceiver(EventReceiver receiver) {
        eventManager.registerReceiver(receiver);
    }
    public void unregisterReceiver(EventReceiver receiver) {
        eventManager.unregisterReceiver(receiver);
    }

    @Override
    public void teardown() {
        for(Player player: playerOrder) {
            player.teardown();
        }
        A = null;
        B = null;

        gameboard.teardown();
        gameboard = null;

        eventManager.teardown();
        eventManager = null;

        currentRound.teardown();
        currentRound = null;
    }//TODO: finish teardown

    public Gameboard getBoard() {
        return gameboard;
    }

    @Override
    public Snapshot takeSnapshot() {
        return new Snapshot();
    }

    //This method will return null if round given is not found in history
    public Snapshot getSnapshotOf(Round r) {
        return history.getOrDefault(r, null);
    }

    public Snapshot getSnapshotOf(Player player, int roundNumber) {
        return history.keySet()
                    .stream()
                    .filter(round ->
                        round.getPlayer().equals(player) && round.getNumber() == roundNumber
                    )
                    .findFirst()
                    .map(key -> history.get(key))
                    .orElse(null);

    }

    public class Snapshot implements eod.snapshots.Snapshot {
        private Player.Snapshot thenPlayer = currentRound.getPlayer().takeSnapshot();
        private Player.Snapshot thenRivalPlayer = getRivalPlayer(currentRound.getPlayer()).takeSnapshot();
        private Gameboard.Snapshot board = gameboard.takeSnapshot();
        private int roundNumber = currentRound.getNumber();

        public Player.Snapshot getThenPlayer() {
            return thenPlayer;
        }

        public Player.Snapshot getThenRivalPlayer() {
            return thenRivalPlayer;
        }

        public Gameboard.Snapshot getBoard() {
            return board;
        }

        public int getRoundNumber() {
            return roundNumber;
        }
    }
}
