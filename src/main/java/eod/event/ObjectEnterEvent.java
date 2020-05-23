package eod.event;

import eod.warObject.WarObject;

public class ObjectEnterEvent implements Event {
    private WarObject object;

    public ObjectEnterEvent(WarObject object) {
        this.object = object;
    }

    public WarObject getObject() {
        return object;
    }
}
