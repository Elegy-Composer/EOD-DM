package eod.specifier.condition;

import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InPointCollectionCondition implements Condition {
    ArrayList<Point> points;

    public InPointCollectionCondition(ArrayList<Point> points) {
        this.points = points;
    }
    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(object -> points.contains(object.position))
                .toArray(WarObject[]::new);
    }
}
