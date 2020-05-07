package eod.specifier.condition;

import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.WarObject;

import java.util.Arrays;

public class InAttackRangeCondition implements Condition{
    WarObject center;
    public InAttackRangeCondition(CanAttack center) {
        this.center = (WarObject) center;
    }

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(this::inRange)
                .toArray(WarObject[]::new);
    }

    private boolean inRange(WarObject target) {
        int dx = Math.abs(target.position.x - center.position.x);
        int dy = Math.abs(target.position.y - center.position.y);

        CanAttack c = (CanAttack) center;
        try {
            return dx <= c.getAttackRange() && dy <= c.getAttackRange();
        } catch (NotSupportedException e) {
            return false;
        }
    }
}
