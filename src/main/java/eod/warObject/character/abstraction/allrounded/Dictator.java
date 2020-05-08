package eod.warObject.character.abstraction.allrounded;

import eod.warObject.character.Character;
import eod.Party;
import eod.Player;

public abstract class Dictator extends Character {
    public Dictator(Player player, int hp, int range, Party party) {
        super(player, hp, range, party);
    }
}
