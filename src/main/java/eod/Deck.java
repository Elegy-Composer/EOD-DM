package eod;

import eod.card.abstraction.Card;
import eod.snapshots.Snapshotted;

import java.util.*;

public class Deck implements Snapshotted, GameObject {

    public List<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
        Collections.shuffle(this.cards);
    }

    public Deck(Card... cards) {
        this.cards = Arrays.asList(cards);
        Collections.shuffle(this.cards);
    }

    public Deck(ArrayList<Card> cards, boolean shuffle) {
        this.cards = cards;
        if (shuffle) {
            Collections.shuffle(this.cards);
        }
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
    public void teardown() {
        cards = null;
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

    public Deck snapshot() {
        ArrayList<Card> newCards = new ArrayList<>();
        for(Card card : cards) {
            Card copy = card.copy();
            newCards.add(copy);
        }

        return new Deck(newCards, false);
    }
}
