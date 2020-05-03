package eod.event.listener;

import eod.Player;
import eod.event.AttackEvent;

public interface AttackListener {
    void onAttack(Player sender, AttackEvent event);
}
