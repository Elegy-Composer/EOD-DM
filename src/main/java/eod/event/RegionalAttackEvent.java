package eod.event;

import eod.Player;
import eod.param.AttackParam;
import eod.warObject.character.abstraction.Character;

import java.awt.*;

public class RegionalAttackEvent extends AttackEvent {
    private Point[] targets;

    public RegionalAttackEvent(Player sender, Character attacker, Point[] targets, AttackParam param) {
        super(sender, attacker, param);
        this.targets = targets;
    }

    public  Point[] getTargets() {
        return targets;
    }
}
