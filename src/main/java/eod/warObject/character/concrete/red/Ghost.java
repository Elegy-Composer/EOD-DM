package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public class Ghost extends Character {

    public Ghost(Player player) {
        super(player, 1, 1, Party.RED);
        // TODO: ask Spacezipper about the details of Ghost
    }

    @Override
    public void attack() {
        // TODO
    }
}
