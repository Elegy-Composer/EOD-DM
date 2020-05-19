package eod.event;

import eod.Player;
import eod.param.AttackParam;
import eod.warObject.Damageable;
import eod.warObject.character.abstraction.Character;

public class DirectAttackEvent extends AttackEvent {
    private Damageable[] targets;

    public DirectAttackEvent(Player sender, Character attacker, Damageable[] targets, AttackParam param) {
        super(sender, attacker, param);
        this.targets = targets;
    }

    public Damageable[] getTargets() {
        return targets;
    }
}
