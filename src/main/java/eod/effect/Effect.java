package eod.effect;

import eod.Player;
import eod.Character;

//Base interface of all effect
public interface Effect {
    default Character askToSelect(Player player, Character[] from) {
        return player.selectCharacter(from);
    }
}
