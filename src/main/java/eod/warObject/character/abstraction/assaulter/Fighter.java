package eod.warObject.character.abstraction.assaulter;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public abstract class Fighter extends Character {
    public Fighter(Player player, int hp, int attack, int range, Party party) {
        super(player, hp, attack, range, party);
    }
}
