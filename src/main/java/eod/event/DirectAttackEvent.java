package eod.event;

import eod.Player;
import eod.warObject.character.abstraction.Character;

public class DirectAttackEvent extends AttackEvent {
    private Character[] targets;

    public DirectAttackEvent(Player sender, Character attacker, Character[] targets, int hp,
                             boolean allowCondition, boolean willSuccess) {
        super(sender, attacker, hp, allowCondition, willSuccess);
        this.targets = targets;
    }

    public Character[] getTargets() {
        return targets;
    }
}
