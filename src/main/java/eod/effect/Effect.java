package eod.effect;

import eod.Player;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

//Base interface of all effect
public interface Effect {

    Player getPlayer();

    default WarObject askToSelectFrom(WarObject[] from) {
        return getPlayer().selectObject(from);
    }

    default Point askToSelectFrom(ArrayList<Point> candidates) {
        return getPlayer().selectPosition(candidates);
    }
}
