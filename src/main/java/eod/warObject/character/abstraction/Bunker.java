package eod.warObject.character.abstraction;

import eod.Party;
import eod.Player;
import eod.warObject.WarObject;

public abstract class Bunker extends Character {
    public Bunker(Player player, int hp, int attack, Party party) {
        super(player, hp, attack, party);
    }
}
