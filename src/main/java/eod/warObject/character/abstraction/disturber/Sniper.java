package eod.warObject.character.abstraction.disturber;

import eod.warObject.character.Character;
import eod.Party;
import eod.Player;

public abstract class Sniper extends Character {
    public Sniper(Player player, int hp, int range, Party party) {
        super(player, hp, range, party);
    }
}
