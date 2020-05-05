package eod.event;

import eod.characters.Character;
import eod.Player;
import eod.card.concrete.conditional.ConditionType;

import java.awt.*;

public class RegionalAttackEvent extends AttackEvent {
    private Point[] targets;

    public RegionalAttackEvent(Player sender, Character attacker, Point[] targets, int hp, boolean allowCondition, boolean willSuccess) {
        super(sender, attacker, hp, allowCondition, willSuccess);
        this.targets = targets;
    }

    public  Point[] getTargets() {
        return targets;
    }

    @Override
    public ConditionType getAttackType() {
        return ConditionType.ATTACK_REGIONAL;
    }
}
