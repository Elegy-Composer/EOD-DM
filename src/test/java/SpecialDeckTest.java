import com.sun.tools.javac.util.List;
import eod.card.abstraction.Card;
import eod.Deck;
import eod.SpecialDeck;
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
        assertEquals(generated.cards, List.from(cards));
    }

    @After
    public void tearDown() {
        playerDeck = null;
    }

}