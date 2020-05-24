package eod.event.relay;

import eod.GameObject;
import eod.event.Event;

import java.util.ArrayList;

public interface EventSender {
    void registerReceiver(EventReceiver receiver);

    void unregisterReceiver(EventReceiver receiver);

    void send(GameObject sender, Event event);
}
