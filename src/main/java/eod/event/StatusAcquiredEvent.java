package eod.event;

import eod.warObject.Status;
import eod.warObject.WarObject;

public class StatusAcquiredEvent implements Event {
    private WarObject object;
    private Status status;

    public StatusAcquiredEvent(WarObject object, Status status) {
        this.object = object;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public WarObject getObject() {
        return object;
    }
}
