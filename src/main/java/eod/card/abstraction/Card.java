package eod.card.abstraction;

public interface Card {

    //When a card doesn't have a cost, ex: passive
    //it's getCost method should return 0
    int getCost();
    String getName();
    CardParty getParty();

    default boolean cardTypeEquals(Class<? extends Card> c) {
        return c.isInstance(this);
    }

}
