package eod;

import eod.card.abstraction.ActionCard;
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

        while(handIsInvalid(A) || handIsInvalid(B)) {
            if(handIsInvalid(playerOrder[0])) {
                Player first = playerOrder[0];

            }
        }

        while(true) {
            try {
                gameLoop();
            } catch (GameLosingException e) {
                break;
            }
        }

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
    }

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
