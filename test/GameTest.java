import eod.Game;
import eod.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private Game game;
    private Player A;
    private Player B;

    @Before
    public void setup() {
        A = new Player();
        B = new Player();
        game = new Game(A, B);
    }

    @Test
    public void gameStartTest() {
        game.start();
    }

    @After
    public void tearDown() {
        game = null;
        A = null;
        B = null;
    }
}