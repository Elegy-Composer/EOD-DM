package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public class LittleGhost extends Character {
    public LittleGhost(Player player) {
        super(player, 1, 1, Party.RED);
        // TODO: ask Spacezipper about the details of LittleGhost
    }

    @Override
    public String getName() {
        return "小亡靈";
    }
}
