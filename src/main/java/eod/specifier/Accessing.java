package eod.specifier;

import eod.specifier.condition.Condition;
import eod.specifier.timing.Timing;
import eod.warObject.WarObject;

public class Accessing {

    private WarObject[] objects;

    public Accessing(WarObject[] objects) {
        this.objects = objects;
    }

    public Accessing which(Condition condition) {
        objects = condition.filter(objects);
        return this;
    }

    public Accessing in(Timing timing) {
        objects = timing.filter(objects);
        return this;
    }

    public WarObject[] get() {
        return objects;
    }
}
