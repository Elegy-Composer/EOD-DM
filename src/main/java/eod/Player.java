package eod;

import eod.card.abstraction.Card;
import eod.card.abstraction.ICard;

import java.util.ArrayList;

public class Player implements Snapshotted{

    private Game game;
    private Deck deck;
    private SpecialDeck specialDeck;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(Deck deck, Game game) {
        this.game = game;
        this.deck = deck;
        specialDeck = SpecialDeck.generateSpecialDeck(deck);

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

    //TODO: implement validateDeck, we didn't do it know because the type of card isn't enough
    public boolean validateDeck() {
        return true;
    }

    @Override
    public Player snapshot() {
        Deck newDeck = deck.snapshot();
        Player clone = new Player(newDeck, game);
        clone.handReceive(hand);

        return clone;
    }

}
