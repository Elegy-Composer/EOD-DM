package eod.event.relay;

import eod.GameObject;
import eod.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class EventManager implements EventSender, GameObject {
    private HashMap<Class<? extends Event>, ArrayList<EventReceiver>> receivers = new HashMap<>();

    @Override
    public void registerReceiver(EventReceiver receiver) {
        for(Class<? extends Event> supportedEvent:receiver.supportedEventTypes()) {
            receivers.putIfAbsent(supportedEvent, new ArrayList<>());
            receivers.get(supportedEvent).add(receiver);
        }
    }

    @Override
    public void unregisterReceiver(EventReceiver receiver) {
        for(Class<? extends Event> supportedEvent:receiver.supportedEventTypes()) {
            receivers.get(supportedEvent).remove(receiver);
        }
    }

    @Override
    public void send(GameObject sender, Event event) {
        receivers.get(event.getClass())
                .forEach(receiver -> receiver.onEventOccurred(sender, event));
    }

    @Override
    public void teardown() {
        for(ArrayList<EventReceiver> subReceivers:receivers.values()) {
            subReceivers.forEach(GameObject::teardown);
        }
        receivers.clear();
        receivers = null;
    }
}
