package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.param.AttackParam;
import eod.warObject.CanAttack;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public class RegionalAttack implements Effect, GameObject {
    // This class should be used only in regional attacks.
    // If there's a direct attack, use Attack.
    private Player player;
    private CanAttack attacker;
    private boolean realDamage = false;
    AttackParam param;

    public RegionalAttack(Player player, int hp) {
        this.player = player;
        param = new AttackParam();
        param.hp = hp;
    }

    public CanAttack attacker() {
        return attacker;
    }

    public RegionalAttack from(WarObject[] objects) {
        attacker = (CanAttack) askToSelectFrom(objects);
        return this;
    }

    public RegionalAttack from(WarObject object) {
        attacker = (CanAttack) object;
        return this;
    }

    public RegionalAttack realDamage() {
        realDamage = true;
        return this;
    }

    public RegionalAttack to(ArrayList<Point> targets) {
        try {
            attacker.attack(targets, param);
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
            Point target = askToSelectFrom(candidates);
            targets.add(target);
            candidates.remove(target);
        }
        try {
            attacker.attack(targets, param);
        } catch (NotSupportedException e) {
            System.out.println(e.toString());
        }
        return this;
    }

    public RegionalAttack to(WarObject[] candidates) {
        WarObject target = askToSelectFrom(candidates);
        ArrayList<Point> singleTarget = new ArrayList<>();
        singleTarget.add(target.position);
        return to(singleTarget);
    }

    @Override
    public void teardown() {
        player = null;
        attacker = null;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
