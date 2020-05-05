package eod.characters.abstraction.disturber;

import eod.characters.Character;
import eod.Party;
import eod.Player;

public abstract class Hacker extends Character {
    public Hacker(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
