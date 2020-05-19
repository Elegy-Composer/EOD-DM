import eod.Game;
import eod.IO.Input;
import eod.IO.LocalInput;
import eod.IO.LocalOutput;
import eod.IO.Output;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.collection.Deck;
import eod.card.concrete.attack.transparent.PreciseShot;
import eod.card.concrete.normal.DroneSupport;
import eod.card.concrete.normal.EmergencyHeal;
import eod.card.concrete.summon.LeadersGuardSummon;
import eod.card.concrete.summon.SpacezipperSummon;
import eod.warObject.leader.red.Sundar;

public class GameStarter {
    public static void main(String[] args) {

        Input AInput = new LocalInput();
        Output AOutput = new LocalOutput();
        Input BInput = new LocalInput();
        Output BOutput = new LocalOutput();

        String AName = AInput.waitForPlayerName();

        Deck ADeck = getDeck();
        Player A = new Player(ADeck, AName);
        for(Card c: ADeck) {
            c.setPlayer(A);
        }
        A.setLeader(new Sundar(A));
        A.attachIO(AInput, AOutput);

        String BName = BInput.waitForPlayerName();

        Deck BDeck = getDeck();
        Player B = new Player(BDeck, BName);
        for(Card c: BDeck) {
            c.setPlayer(B);
        }
        B.setLeader(new Sundar(B));
        B.attachIO(BInput, BOutput);

        Game game = new Game(A, B);
        game.start();

    }

    public static Deck getDeck() {
        return new Deck(
                new DroneSupport(),
                new EmergencyHeal(),
                new PreciseShot(),
                new SpacezipperSummon(),
                new LeadersGuardSummon()
        );
    }
}
