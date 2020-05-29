package eod.event.relay;

import eod.GameObject;
import eod.event.Event;

import java.util.ArrayList;

public class EventManager implements EventSender, GameObject {
    private ArrayList<EventReceiver> receivers = new ArrayList<>();

    @Override
    public void registerReceiver(EventReceiver receiver) {
        receivers.add(receiver);
    }

    @Override
    public void unregisterReceiver(EventReceiver receiver) {
        receivers.remove(receiver);
    }

    @Override
    public EventReceiver[] getStatusHolders() {
        return receivers.stream().filter(receiver -> receiver instanceof StatusHolder).toArray(EventReceiver[]::new);
    }

    @Override
    public void send(GameObject sender, Event event) {
        receivers.stream()
                .filter(receiver -> receiver.supportedEventTypes().contains(event.getClass()))
                .forEach(receiver -> receiver.onEventOccurred(sender, event));
    }

    @Override
    public void teardown() {
        receivers.clear();
        receivers = null;
    }
}
