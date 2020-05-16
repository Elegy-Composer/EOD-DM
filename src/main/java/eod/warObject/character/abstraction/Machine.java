package eod.warObject.character.abstraction;

import eod.Party;
import eod.Player;
import eod.warObject.WarObject;

public abstract class Machine extends Character {
    public Machine(Player player, int hp, int attack, int range, Party party) {
        super(player, hp, attack, range, party);
    }
}
