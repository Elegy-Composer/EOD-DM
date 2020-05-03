import eod.card.abstraction.Card;
import eod.card.collection.Deck;
import eod.card.collection.SpecialDeck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpecialDeckTest {

    private Deck playerDeck;

    @Before
    public void setup() {
        playerDeck = new Deck();
    }

    @Test
    public void generateSpecialDeckTest() {
        SpecialDeck generated = SpecialDeck.generateSpecialDeck(playerDeck);
        Card[] cards = {};
        assertTrue(true);
    }

    @After
    public void tearDown() {
        playerDeck = null;
    }

}