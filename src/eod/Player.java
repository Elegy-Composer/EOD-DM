package eod;

import eod.card.abstraction.Card;

import java.util.ArrayList;

public class Player {

    private Deck deck;
    private SpecialDeck specialDeck;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(Deck deck) {
        this.deck = deck;
        specialDeck = SpecialDeck.generateSpecialDeck(deck);

    }

    public void drawFromDeck(int count) {
        deck.draw(count);
    }

    public boolean checkInHand(Class<? extends Card> c) {
        for(Card card: hand) {
            if (card.cardTypeEquals(c)) {
                return true;
            }
        }
        return false;
    }

    //TODO: implement validateDeck, we didn't do it know because the type of card isn't enough
    public boolean validateDeck() {
        return true;
    }
}
