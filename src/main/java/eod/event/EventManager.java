package eod.event;

import eod.GameObject;
import eod.Player;
import eod.event.listener.EventListener;

import java.util.ArrayList;

public class EventManager implements GameObject {
    private ArrayList<EventListener> listeners = new ArrayList<>();

    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(EventListener listener) {
        listeners.remove(listener);
    }

    public void send(GameObject sender, Event event) {
        listeners.stream()
                .filter(listener -> listener.supportedEventTypes().contains(event.getClass()))
                .forEach(listener -> listener.onEventOccurred(sender, event));
    }

    @Override
    public void teardown() {
        listeners.clear();
        listeners = null;
    }
}
