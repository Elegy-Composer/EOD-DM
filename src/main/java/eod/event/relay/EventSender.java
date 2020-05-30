package eod.event.relay;

import eod.GameObject;
import eod.event.Event;

public interface EventSender {
    void registerReceiver(Class<? extends Event> supportedType, EventReceiver receiver);

    void unregisterReceiver(Class<? extends Event> supportedType, EventReceiver receiver);

    EventReceiver[] getStatusHolders();

    void send(GameObject sender, Event event);
}
