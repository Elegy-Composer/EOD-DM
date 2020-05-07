package eod.warObject.character.abstraction.allrounded;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public abstract class Dictator extends Character {
    public Dictator(Player player, int hp, int range, Party party) {
        super(player, hp, range, party);
    }
}
