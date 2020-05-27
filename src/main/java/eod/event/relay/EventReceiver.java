package eod.event.relay;

import eod.GameObject;
import eod.event.Event;

import java.util.ArrayList;

public interface EventReceiver extends GameObject {
    public ArrayList<Class<? extends Event>> supportedEventTypes();
    void onEventOccurred(GameObject sender, Event event);
}
