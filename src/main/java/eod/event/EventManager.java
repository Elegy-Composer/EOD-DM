package eod.event;

import eod.event.listener.AttackListener;

import java.util.ArrayList;

public class EventManager {
    private ArrayList<AttackListener> attackListeners = new ArrayList<>();

    public void registerAttackEvent(AttackListener listener) {
        attackListeners.add(listener);
    }

    public void unregisterAttackEvent(AttackListener listener) {
        attackListeners.remove(listener);
    }

    public void sendAttack(AttackEvent event) {
        for(AttackListener listener: attackListeners) {
            listener.onAttack(event.getSender(), event);
        }
    }
}
