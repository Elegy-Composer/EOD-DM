package eod.event.relay;

import eod.GameObject;
import eod.event.Event;

public interface EventSender {
    void registerReceiver(EventReceiver receiver);

    void unregisterReceiver(EventReceiver receiver);

    EventReceiver[] getStatusHolders();

    void send(GameObject sender, Event event);
}
