package eod.warObject.character.abstraction.assaulter;

import eod.warObject.character.Character;
import eod.Party;
import eod.Player;

public abstract class Shooter extends Character {
    public Shooter(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}