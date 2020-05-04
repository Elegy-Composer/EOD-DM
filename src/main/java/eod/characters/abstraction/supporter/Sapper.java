package eod.characters.abstraction.supporter;

import eod.Character;
import eod.Party;
import eod.Player;

public abstract class Sapper extends Character {
    public Sapper(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
