package eod.card.collection;

import eod.GameObject;
import eod.card.abstraction.Card;
import eod.exceptions.GameLosingException;
import eod.snapshots.Snapshotted;

import java.util.*;
import java.util.stream.Collectors;

public class Deck implements Snapshotted<Deck.Snapshot>, Iterable<Card>,  GameObject {

    public ArrayList<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
        Collections.shuffle(this.cards);
    }

    public Deck(Card... cards) {
        this.cards = new ArrayList<>(Arrays.asList(cards));
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
            Card[] drew = cards.subList(0, count).toArray(new Card[0]);
            cards.subList(0, count).clear();
            return drew;
        } catch (IndexOutOfBoundsException exception) {
            throw new GameLosingException("Deck is drained");
        }

    }

    public Card[] draw(Class<? extends Card> cardType, int count) {
        List<Card> canDraw = cards.stream().filter(card -> card.cardTypeEquals(cardType)).collect(Collectors.toList());
        int n = Math.min(canDraw.size(), count);

        Card[] drew = new Card[n];

        for(int i = 0;i < n;i++) {
            drew[i] = canDraw.remove(0);
            cards.remove(drew[i]);
        }

        if(cards.size() == 0) {
            throw new GameLosingException("Deck is drained.");
        }

        return drew;
    }

    public void dropCard(Card card) {
        for(Card c:cards) {
            if(c.cardTypeEquals(card.getClass())) {
                cards.remove(c);
                break;
            }
        }
        Collections.shuffle(cards);
    }

    public void dropAll(Card card) {
        cards.removeIf(c -> c.cardTypeEquals(card.getClass()));
    }

    @Override
    public void teardown() {
        cards.clear();
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

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public Snapshot takeSnapshot() {
        return new Snapshot();
    }

    public class Snapshot implements eod.snapshots.Snapshot {
        private ArrayList<Card> cardsSnapshot = new ArrayList<>();

        public Snapshot() {
            for(Card card : cards) {
                Card copy = card.copy();
                cardsSnapshot.add(copy);
            }
        }

        public ArrayList<Card> getCards() {
            return cardsSnapshot;
        }
    }
}
