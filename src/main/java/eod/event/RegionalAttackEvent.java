package eod.event;

import eod.Character;
import eod.Player;
import eod.effect.RegionalAttack;

import java.awt.*;

public class RegionalAttackEvent extends AttackEvent{
    private Point[] targets;

    public RegionalAttackEvent(Player sender, Character attacker, Point[] targets, int hp, boolean allowCondition, boolean willSuccess) {
        super(sender, attacker, hp, allowCondition, willSuccess);
    }

    public  Point[] getTargets() {
        return targets;
    }
}
