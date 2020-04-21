package eod;

import eod.card.abstraction.Card;

import java.util.ArrayList;
import java.util.Objects;

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
                Objects.equals(specialDeck, player.specialDeck) &&
                Objects.equals(hand, player.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck, specialDeck, hand);
    }
}
