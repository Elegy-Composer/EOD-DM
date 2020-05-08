package eod;

import eod.card.abstraction.Card;
import eod.card.abstraction.action.ActionCard;
import eod.event.EventManager;
import eod.event.RoundEndEvent;
import eod.event.RoundStartEvent;
import eod.event.listener.EventListener;
import eod.exceptions.GameLosingException;
import eod.snapshots.Snapshotted;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

//represent a game instance
//each manages a ongoing game
public class Game implements Snapshotted<Game.Snapshot>, GameObject {

    private Player A;
    private Player B;
    private Gameboard gameboard;
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
    }


    public void start() {
        A.validateDeck();
        B.validateDeck();

        playerOrder = decidePlayerOrder();
        A.drawFromDeck(3);
        B.drawFromDeck(3);

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
        } while(drawRound<3 || hasInvalid);


        // the second player should receive a {placeholder} at the start
        ArrayList<Card> firstHand = new ArrayList<>();
        // TODO: add the first card of the second player
        playerOrder[1].handReceive(firstHand);

        currentRound = new Round(playerOrder[0], 1);
        while(true) {
            try {
                eventManager.send(this, new RoundStartEvent(currentRound));

                gameLoop();

                eventManager.send(this, new RoundEndEvent(currentRound));
                updateHistory();
                currentRound = nextRound();
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

    private Round nextRound() {
        if(currentRound.getPlayer().equals(playerOrder[0])) {
            return new Round(playerOrder[1], currentRound.getNumber());
        } else {
            return new Round(playerOrder[0], currentRound.getNumber() + 1);
        }
    }

    private void updateHistory() {
        history.put(currentRound, takeSnapshot());
        if(history.size() > maxHistoryLength) {
            Round first = history.keySet().toArray(new Round[0])[0];
            history.remove(first);
        }
    }

    public Player getRivalPlayer(Player player) {
        if(player == playerOrder[0]) {
            return playerOrder[1];
        } else {
            return playerOrder[0];
        }
    }

    public boolean isPlayerA(Player unknown) {
        return unknown.equals(A);
    }

    private boolean handIsInvalid(Player player) {
        return !player.checkInHand(ActionCard.class);
    }

    private void gameLoop() throws GameLosingException {
    }

    public void registerListener(EventListener listener) {
        eventManager.registerAttackEvent(listener);
    }
    public void unregisterListener(EventListener listener) {
        eventManager.unregisterAttackEvent(listener);
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
