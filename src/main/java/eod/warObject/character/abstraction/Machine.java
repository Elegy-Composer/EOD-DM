package eod.warObject.character.abstraction;

import eod.Party;
import eod.Player;

public abstract class Machine extends Character {
    public Machine(Player player, int hp, int attack, Party party) {
        super(player, hp, attack, party);
    }
}
