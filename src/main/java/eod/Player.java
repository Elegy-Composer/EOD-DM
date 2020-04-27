package eod;

import eod.card.abstraction.Card;
import eod.card.abstraction.ICard;
import eod.snapshots.Snapshotted;

import java.util.ArrayList;
import java.util.Objects;

public class Player implements Snapshotted, GameObject {

    private Deck deck;
    private Game game;
    private SpecialDeck specialDeck;
    private ArrayList<Card> hand = new ArrayList<>();
    private Leader leader;

    public Player(Deck deck, Game game, Leader leader) {
        this.game = game;
        this.deck = deck;
        specialDeck = SpecialDeck.generateSpecialDeck(deck);
        this.leader = leader;
    }

    public void handReceive(ArrayList<Card> h) {
        hand.addAll(h);
    }

    public void drawFromDeck(int count) {
        deck.draw(count);
    }

    public boolean checkInHand(Class<? extends ICard> c) {
        for(Card card: hand) {
            if (card.cardTypeEquals(c)) {
                return true;
            }
        }
        return false;
    }

    //TODO: implement validateDeck, we didn't do it know because the types of card aren't enough
    public boolean validateDeck() {
        return true;
    }

    public Gameboard getBoard() {
        return game.getBoard();
    }

    public boolean isLeaderAlive() {
        return leader.isAlive();
    }

    @Override
    public void teardown() {
        //TODO: finish teardown
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

    @Override
    public Player snapshot() {
        Deck newDeck = deck.snapshot();
        Player clone = new Player(newDeck, game, leader);
        clone.handReceive(hand);

        return clone;
    }
}
