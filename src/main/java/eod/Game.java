package eod;

import eod.card.abstraction.ActionCard;
import eod.exceptions.GameLosingException;
import eod.snapshots.BoardSnapshot;
import eod.snapshots.GameSnapshot;
import eod.snapshots.Snapshotted;

import java.util.Random;

//represent a game instance
//each manages a ongoing game
public class Game implements Snapshotted, GameObject {

    private Player A;
    private Player B;
    private Gameboard gameboard;
    private Player[] playerOrder;

    public Game(Player A, Player B) {
        this.A = A;
        this.B = B;
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

        while(true) {
            try {
                gameLoop();
            } catch (GameLosingException e) {
                break;
            }
        }

        // TODO: things to do when a person wins
        if(A.isLeaderAlive()) {
            //A wins
        } else if(B.isLeaderAlive()) {
            //B wins
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

    private boolean handIsInvalid(Player player) {
        return !player.checkInHand(ActionCard.class);
    }

    private void gameLoop() throws GameLosingException {

    }

    @Override
    public void teardown() {
        for(Player player: playerOrder) {
            player.teardown();
            gameboard.teardown();
        }
        A = null;
        B = null;
        gameboard = null;
    }//TODO: finish teardown

    public Gameboard getBoard() {
        return gameboard;
    }

    @Override
    public GameSnapshot snapshot() {
        Player Aclone = A.snapshot();
        Player Bclone = B.snapshot();
        BoardSnapshot boardSnapshot = gameboard.snapshot();

        return new GameSnapshot(Aclone, Bclone, boardSnapshot);
    }
}
