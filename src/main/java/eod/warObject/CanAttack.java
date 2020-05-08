package eod.warObject;

import eod.warObject.Touchable;

import java.awt.*;
import java.util.ArrayList;

public interface CanAttack {
    void attack();
    void attack(ArrayList<Point> targets, int hp);
    void attack(Touchable target, int hp);
    void attack(Touchable[] targets, int hp);
    int getAttackRange();
}
