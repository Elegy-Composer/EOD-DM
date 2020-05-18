import eod.IO.Input;
import eod.IO.LocalInput;
import eod.IO.LocalOutput;
import eod.IO.Output;
import eod.Player;
import eod.card.collection.Deck;
import eod.card.concrete.attack.transparent.PreciseShot;
import eod.card.concrete.normal.DroneSupport;
import eod.card.concrete.normal.EmergencyHeal;
import eod.warObject.leader.red.Sundar;

public class GameStarter {
    public static void main(String[] args) {

        Input AInput = new LocalInput();
        Output AOutput = new LocalOutput();
        Input BInput = new LocalInput();
        Output BOutput = new LocalOutput();

        String AName = AInput.waitForPlayerName();
        //Deck ADeck = getADeck();
        //Player A = new Player(ADeck, AName);
        //A.setLeader(new Sundar(A));
    }

    public static Deck getADeck(Player A) {
        return new Deck(
                new DroneSupport(A),
                new EmergencyHeal(A),
                new PreciseShot(A)

        );
    }
}
