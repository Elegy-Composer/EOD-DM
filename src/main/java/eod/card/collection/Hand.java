package eod.card.collection;

import eod.GameObject;
import eod.card.abstraction.Card;
import eod.snapshots.Snapshotted;

import java.util.*;
import java.util.stream.Stream;

public class Hand implements GameObject, Iterable<Card>, Snapshotted<Hand.Snapshot> {
    private ArrayList<Card> hand = new ArrayList<>();

    public void receive(ArrayList<Card> cards) {
        hand.addAll(cards);
    }

    public void dropCard(Card card) {
        for(Card c:hand) {
            if(c.cardTypeEquals(card.getClass())) {
                hand.remove(c);
                break;
            }
        }
    }

    public void dropAll(Card card) {
        hand.removeIf(c -> c.cardTypeEquals(card.getClass()));
    }

    public void dropAllCards() {
        hand.clear();
    }

    public void randomlyDropCards(int num) throws IllegalArgumentException {
        if(num < 0) {
            throw new IllegalArgumentException("Attempting to drop negative number of cards");
        }
        if(num >=hand.size()) {
            dropAllCards();
            return;
        }

        Random random = new Random();
        for(int i = 0;i < num;i++) {
            int toDrop = random.nextInt(hand.size());
            hand.remove(toDrop);
        }
    }

    public boolean containsType(Class<? extends Card> c) {
        for(Card card: hand) {
            if (card.cardTypeEquals(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsCard(Card c) {
        return hand.contains(c);
    }

    public Stream<Card> stream() {
        return hand.stream();
    }
    public Card[] toArray() {
        return hand.toArray(new Card[0]);
    }

    public void remove(Card c) {
        hand.remove(c);
    }

    @Override
    public void teardown() {
        hand.clear();
        hand = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hand hand1 = (Hand) o;
        return Objects.equals(hand, hand1.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hand);
    }

    @Override
    public Iterator<Card> iterator() {
        return hand.iterator();
    }

    @Override
    public Snapshot takeSnapshot() {
        return new Snapshot();
    }

    public class Snapshot implements eod.snapshots.Snapshot {
        private ArrayList<Card> cards = new ArrayList<>();

        public Snapshot() {
            for (Card card : hand) {
                Card copy = card.copy();
                cards.add(copy);
            }
        }

        public ArrayList<Card> getCards() {
            return cards;
        }
    }
}
