package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public class GhostOfHatred extends Character {
    public GhostOfHatred(Player player) {
        super(player, 1, 1, Party.RED);
        // TODO: ask Spacezipper about the details of GhostOfHatred
    }


    @Override
    public String getName() {
        return "怨恨的亡靈";
    }
}
