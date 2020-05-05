package eod.event.listener;

import eod.Player;
import eod.event.Event;

public interface EventListener {
    void onEventOccured(Player sender, Event event);
}
