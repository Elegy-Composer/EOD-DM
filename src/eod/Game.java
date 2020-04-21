package eod;

import eod.card.abstraction.ActionCard;
import eod.snapshots.BoardSnapshot;
import eod.snapshots.GameSnapshot;

//represent a game instance
//each manages a ongoing game
public class Game implements Snapshotted{

    private Player A;
    private Player B;
    private Gameboard gameboard;
    private Player[] order;

    public Game(Player A, Player B) {
        this.A = A;
        this.B = B;
    }


    public void start() {

        A.validateDeck();
        B.validateDeck();

        order = decidePlayerOrder();
        A.drawFromDeck(3);
        B.drawFromDeck(3);

        while(handIsInvalid(A) || handIsInvalid(B)) {
            if(handIsInvalid(order[0])) {
                Player first = order[0];

            }
        }
    }

    private Player[] decidePlayerOrder() {
        boolean AFirst = Math.round(Math.random()) == 0;
        if(AFirst) {
            return new Player[]{A, B};
        } else {
            return new Player[]{B, A};
        }
    }

    private boolean handIsInvalid(Player player) {
        return !player.checkInHand(ActionCard.class);
    }

    @Override
    public GameSnapshot snapshot() {
        Player Aclone = A.snapshot();
        Player Bclone = B.snapshot();
        BoardSnapshot boardSnapshot = Gameboard.getSnapshot();

        return new GameSnapshot(Aclone, Bclone, boardSnapshot);
    }

    /* TODO: while closing the game, tells players to remove decks, decks to remove cards,
        and the game itself should remove the players afterwards to avoid circular reference */
}
