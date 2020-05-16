package eod.specifier.condition;

import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InAttackRangeCondition implements Condition{
    WarObject center;
    ArrayList<Point> attackRange;
    public InAttackRangeCondition(CanAttack center) {
        this.center = (WarObject) center;
        try {
            attackRange = center.getAttackRange();
        } catch (NotSupportedException e) {
            attackRange = new ArrayList<>();
        }
    }

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(this::inRange)
                .toArray(WarObject[]::new);
    }

    private boolean inRange(WarObject target) {
        return attackRange.contains(target.position);
    }
}
