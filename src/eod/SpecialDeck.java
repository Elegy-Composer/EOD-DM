package eod;

public class SpecialDeck extends Deck {

    private SpecialDeck() {

    }

    public static SpecialDeck generateSpecialDeck(Deck playerDeck) {
        return new SpecialDeck();
    }
}
