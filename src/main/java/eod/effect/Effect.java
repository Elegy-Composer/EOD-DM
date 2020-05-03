package eod.effect;

import eod.Player;
import eod.Character;

//Base interface of all effect
public interface Effect {

    Player getPlayer();

    default Character askToSelectFrom(Character[] from) {
        return getPlayer().selectCharacter(from);
    }
}
