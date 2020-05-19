package eod.event;

import eod.warObject.CanAttack;
import eod.warObject.Damageable;

public class TargetedEvent implements Event {
    private CanAttack attacker;
    private Damageable target;

    public TargetedEvent(CanAttack attacker, Damageable target) {
        this.attacker = attacker;
        this.target = target;
    }

    public CanAttack getAttacker() {
        return attacker;
    }

    public Damageable getTarget() {
        return target;
    }
}
