package eod.event;

import eod.warObject.WarObject;

import java.awt.*;

public class ObjectMovingEvent implements Event {
    private WarObject object;
    Point origPos, newPos;

    public ObjectMovingEvent(WarObject object, Point newPos) {
        this.object = object;
        origPos = object.position;
        this.newPos = newPos;
    }

    public WarObject getObject() {
        return object;
    }

    public Point getOrigPos() {
        return origPos;
    }

    public Point getNewPos() {
        return newPos;
    }
}
