package eod.warObject.character.abstraction.supporter;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

public abstract class Medic extends Character {
    public Medic(Player player, int hp, int attack, Party party) {
        super(player, hp, attack, party);
    }
}
