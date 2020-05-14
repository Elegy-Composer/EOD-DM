package eod.event;

import eod.warObject.Damageable;

public class ObjectDeadEvent implements Event {
    private Damageable object;

    public ObjectDeadEvent(Damageable object) {
        this.object = object;
    }

    public Damageable getDeadObject() {
        return object;
    }
}
