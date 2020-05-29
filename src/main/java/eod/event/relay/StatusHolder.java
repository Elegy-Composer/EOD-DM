package eod.event.relay;

import eod.warObject.Status;

import java.util.ArrayList;

public interface StatusHolder extends EventReceiver {
    // the receiver that holds status for a limited time
    // It will only remove the status if no other holder is holding its statuses.
    ArrayList<Status> holdingStatus();
}
