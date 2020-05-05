package eod.event.listener;

import eod.GameObject;
import eod.Player;
import eod.event.Event;

import java.util.ArrayList;

public interface EventListener {
    public ArrayList<Class<? extends Event>> supportedEventTypes();
    void onEventOccurred(GameObject sender, Event event);
}
