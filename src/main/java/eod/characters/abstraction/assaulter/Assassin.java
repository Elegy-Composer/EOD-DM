package eod.characters.abstraction.assaulter;

import eod.Character;
import eod.Party;
import eod.Player;

public abstract class Assassin extends Character {
    public Assassin(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
