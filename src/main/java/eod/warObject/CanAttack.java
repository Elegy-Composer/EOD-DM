package eod.warObject;

import eod.exceptions.NotSupportedException;

import java.awt.*;
import java.util.ArrayList;

public interface CanAttack {

    default void attack() throws NotSupportedException {
        throw new NotSupportedException("This method isn't supported by the object.");
    }

    default void attack(ArrayList<Point> targets, int hp) throws NotSupportedException {
        throw new NotSupportedException("This method isn't supported by the object.");
    }

    default void attack(Damageable target, int hp) throws NotSupportedException {
        throw new NotSupportedException("This method isn't supported by the object.");
    }
    default void attack(Damageable[] targets, int hp) throws NotSupportedException {
        throw new NotSupportedException("This method isn't supported by the object.");
    }

    default int getAttackRange() throws NotSupportedException {
        throw new NotSupportedException("This method isn't supported by the object.");
    }
}
