package eod.warObject.character.abstraction.supporter;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public abstract class Sapper extends Character {
    public Sapper(Player player, int hp, int range, Party party) {
        super(player, hp, range, party);
    }
}
