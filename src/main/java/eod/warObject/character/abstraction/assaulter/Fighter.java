package eod.warObject.character.abstraction.assaulter;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public abstract class Fighter extends Character {
    public Fighter(Player player, int hp, int range, Party party) {
        super(player, hp, range, party);
    }
}
