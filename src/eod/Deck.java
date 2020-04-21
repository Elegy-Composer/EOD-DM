package eod;

import eod.card.abstraction.Card;

import java.util.*;

public class Deck {

    public List<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
        Collections.shuffle(this.cards);
    }

    public Deck(Card... cards) {
        this.cards = Arrays.asList(cards);
        Collections.shuffle(this.cards);
    }

    /**
     * This method will return all the cards left if the number of requested cards exceeds the number of cards in deck.
     * If the deck is empty, it will return an empty array.
    */
    public Card[] draw(int count) {
        try {
            Card[] drew = cards.subList(0, count-1).toArray(new Card[0]);
            cards.subList(0, count-1).clear();
            return drew;
        } catch (IndexOutOfBoundsException exception) {
            throw new GameLosingException("Deck is drained");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deck deck = (Deck) o;
        return Objects.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }
}
