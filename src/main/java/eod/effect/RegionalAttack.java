package eod.effect;

import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public class RegionalAttack extends Attack {
    // This class should be used only in regional attacks.
    // If there's a direct attack, use Attack.
    public RegionalAttack(Player player, int hp) {
        super(player, hp);
    }

    public RegionalAttack from(WarObject[] objects) {
        attacker = (CanAttack) askToSelectOneFrom(objects);
        return this;
    }

    public RegionalAttack from(WarObject object) {
        attacker = (CanAttack) object;
        return this;
    }

    public RegionalAttack realDamage() {
        param.realDamage = true;
        return this;
    }

    public RegionalAttack to(ArrayList<Point> targets) {
        try {
            affected.addAll(attacker.attack(targets, param));
        } catch (NotSupportedException e) {
            System.out.println(e.toString());
        }
        return this;
    }

    public RegionalAttack to(ArrayList<Point> candidates, int number) {
        if(number >= candidates.size()) {
            return to(candidates);
        }

        ArrayList<Point> targets = new ArrayList<>();
        for(int i = 0;i < number;i++) {
            Point target = askToSelectOneFrom(candidates);
            targets.add(target);
            candidates.remove(target);
        }
        return to(targets);
    }

    public RegionalAttack to(WarObject[] candidates) {
        WarObject target = askToSelectOneFrom(candidates);
        ArrayList<Point> singleTarget = new ArrayList<>();
        singleTarget.add(target.position);
        return to(singleTarget);
    }

    public RegionalAttack to(WarObject target) {
        ArrayList<Point> singleTarget = new ArrayList<>();
        singleTarget.add(target.position);
        return to(singleTarget);
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
