package eod.warObject.character.abstraction.supporter;

import eod.warObject.character.Character;
import eod.Party;
import eod.Player;

public abstract class Medic extends Character {
    public Medic(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
