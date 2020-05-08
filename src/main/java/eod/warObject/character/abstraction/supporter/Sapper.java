package eod.warObject.character.abstraction.supporter;

import eod.warObject.character.Character;
import eod.Party;
import eod.Player;

public abstract class Sapper extends Character {
    public Sapper(Player player, int hp, int range, Party party) {
        super(player, hp, range, party);
    }
}
