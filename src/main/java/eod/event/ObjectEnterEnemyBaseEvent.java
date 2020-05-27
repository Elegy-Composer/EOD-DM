package eod.event;

import eod.warObject.WarObject;

public class ObjectEnterEnemyBaseEvent implements Event {
    private WarObject object;

    public ObjectEnterEnemyBaseEvent(WarObject object) {
        this.object = object;
    }

    public WarObject getObject() {
        return object;
    }
}
