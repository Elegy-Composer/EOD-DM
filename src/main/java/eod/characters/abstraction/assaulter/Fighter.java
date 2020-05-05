package eod.characters.abstraction.assaulter;

import eod.characters.Character;
import eod.Party;
import eod.Player;

public abstract class Fighter extends Character {
    public Fighter(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
