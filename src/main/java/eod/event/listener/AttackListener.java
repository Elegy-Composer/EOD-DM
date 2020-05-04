package eod.event.listener;

import eod.Player;
import eod.event.AttackEvent;
import eod.event.DirectAttackEvent;

public interface AttackListener {
    void onAttack(Player sender, AttackEvent event);
}
