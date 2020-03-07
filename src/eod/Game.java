package eod;

import eod.card.abstraction.ActionCard;

//represent a game instance
//each manages a ongoing game
public class Game {

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
}
