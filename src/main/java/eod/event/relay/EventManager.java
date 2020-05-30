package eod.event.relay;

import eod.GameObject;
import eod.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class EventManager implements EventSender, GameObject {
    private HashMap<Class<? extends Event>, ArrayList<EventReceiver>> receivers = new HashMap<>();

    @Override
    public void registerReceiver(Class<? extends Event> supportedType, EventReceiver receiver) {
        receivers.putIfAbsent(supportedType, new ArrayList<>());
        receivers.get(supportedType).add(receiver);
    }

    @Override
    public void unregisterReceiver(Class<? extends Event> supportedType, EventReceiver receiver) {
        receivers.get(supportedType).remove(receiver);
    }

    @Override
    public EventReceiver[] getStatusHolders() {
        ArrayList<EventReceiver> holders = new ArrayList<>();
        for(ArrayList<EventReceiver> subReceiver:receivers.values()) {
            holders.addAll(subReceiver.stream().filter(receiver -> receiver instanceof StatusHolder).collect(Collectors.toList()));
        }
        return holders.toArray(EventReceiver[]::new);
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
