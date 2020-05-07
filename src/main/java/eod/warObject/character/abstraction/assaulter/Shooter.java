package eod.warObject.character.abstraction.assaulter;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public abstract class Shooter extends Character {
    public Shooter(Player player, int hp, int range, Party party) {
        super(player, hp, range, party);
    }
}
