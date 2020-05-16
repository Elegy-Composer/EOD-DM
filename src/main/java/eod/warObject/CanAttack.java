package eod.warObject;

import eod.exceptions.NotSupportedException;

import java.awt.*;
import java.util.ArrayList;

public interface CanAttack {
    String ERR_MSG = "This method isn't supported by the object.";

    default void attack() throws NotSupportedException {
        throw new NotSupportedException(ERR_MSG);
    }

    default void attack(ArrayList<Point> targets, int hp) throws NotSupportedException {
        throw new NotSupportedException(ERR_MSG);
    }

    default void attack(Damageable target, int hp) throws NotSupportedException {
        throw new NotSupportedException(ERR_MSG);
    }
    default void attack(Damageable[] targets, int hp) throws NotSupportedException {
        throw new NotSupportedException(ERR_MSG);
    }

    default void realAttack(ArrayList<Point> targets, int hp) throws NotSupportedException {
        throw new NotSupportedException(ERR_MSG);
    }

    default ArrayList<Point> getAttackRange() throws NotSupportedException {
        throw new NotSupportedException(ERR_MSG);
    }
}
