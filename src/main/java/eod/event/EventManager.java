package eod.event;

import eod.GameObject;
import eod.event.listener.EventListener;

import java.util.ArrayList;

public class EventManager implements GameObject {
    private ArrayList<EventListener> attackListeners = new ArrayList<>();

    public void registerAttackEvent(EventListener listener) {
        attackListeners.add(listener);
    }

    public void unregisterAttackEvent(EventListener listener) {
        attackListeners.remove(listener);
    }

    @Override
    public void teardown() {
        attackListeners.clear();
        attackListeners = null;
    }
}
