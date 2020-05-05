package eod.effect;

import eod.Player;
import eod.characters.Character;

import java.awt.*;
import java.util.ArrayList;

//Base interface of all effect
public interface Effect {

    Player getPlayer();

    default Character askToSelectFrom(Character[] from) {
        return getPlayer().selectCharacter(from);
    }

    default Point askToSelectFrom(ArrayList<Point> candidates) {
        return getPlayer().selectPosition(candidates);
    }
}
