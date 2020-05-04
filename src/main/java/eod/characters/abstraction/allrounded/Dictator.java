package eod.characters.abstraction.allrounded;

import eod.Character;
import eod.Party;
import eod.Player;

public abstract class Dictator extends Character {
    public Dictator(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
