package eod.effect;

import eod.Player;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

//Base interface of all effect
public interface Effect {

    Player getPlayer();

    default WarObject askToSelectOneFrom(WarObject[] from) {
        return getPlayer().selectObject(from);
    }

    default WarObject[] askToSelectMultipleFrom(WarObject[] from, int number) {
        return getPlayer().selectMultipleObject(from, number);
    }

    default Point askToSelectOneFrom(ArrayList<Point> candidates) {
        return getPlayer().selectPosition(candidates);
    }
}
