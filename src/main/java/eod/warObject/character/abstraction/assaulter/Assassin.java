package eod.warObject.character.abstraction.assaulter;

import eod.warObject.character.Character;
import eod.Party;
import eod.Player;

public abstract class Assassin extends Character {
    public Assassin(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
