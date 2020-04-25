package eod.card.abstraction;

import eod.Deck;

public interface ICard {

    //When a card doesn't have a cost, ex: passive
    //it's getCost method should return 0
    int getCost();
    String getName();
    CardParty getParty();
    Deck ownerDeck = null;

    default boolean cardTypeEquals(Class<? extends ICard> c) {
        return c.isInstance(this);
    }

}
