package eod.warObject.character.abstraction.supporter;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public abstract class Sapper extends Character {
    public Sapper(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
