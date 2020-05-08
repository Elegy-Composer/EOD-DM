package eod.warObject.character.concrete.red;

import eod.Player;
import eod.warObject.character.abstraction.other.Ghost;

public class GhostOfHatred extends Ghost {
    public GhostOfHatred(Player player) {
        super(player, 1, 1);
        // TODO: ask Spacezipper about the details of GhostOfHatred
    }


    @Override
    public String getName() {
        return "怨念的亡靈";
    }
}
