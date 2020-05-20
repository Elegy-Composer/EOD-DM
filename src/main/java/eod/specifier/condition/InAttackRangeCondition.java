package eod.specifier.condition;

import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InAttackRangeCondition implements Condition{
    private WarObject center;
    private ArrayList<Point> points;

    public InAttackRangeCondition(CanAttack center) {
        points = new ArrayList<>();
        this.center = (WarObject) center;
        try {
            points = center.getAttackRange();
        } catch (NotSupportedException e) {
            System.out.println("The center "+((WarObject) center).getName()+" doesn't have the attack range");
        }
    }

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(object -> points.contains(object.position))
                .toArray(WarObject[]::new);
    }
}
