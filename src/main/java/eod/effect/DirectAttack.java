package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.param.AttackParam;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

public class DirectAttack implements Effect, GameObject {
    // This class should be used only in direct attacks.
    // If there's a ranged attack, use RegionalAttack.
    private Player player;
    private CanAttack attacker;
    private Damageable target;
    private AttackParam param;

    public DirectAttack(Player player, int hp) {
        param = new AttackParam();
        param.hp = hp;
        this.player = player;
    }

    public CanAttack attacker() {
        return attacker;
    }

    public DirectAttack from(WarObject[] objects) {
        attacker = (CanAttack) askToSelectFrom(objects);
        return this;
    }

    public DirectAttack from(WarObject object) {
        attacker = (CanAttack) object;
        return this;
    }

    public DirectAttack to(WarObject[] objects) {
        target = (Damageable) askToSelectFrom(objects);
        try {
            attacker.attack(target, param);
        } catch (NotSupportedException e) {
            System.out.println(e.toString());
        }
        return this;
    }

    public DirectAttack toAll(Damageable[] targets) {
        for(Damageable target:targets) {
            try {
                attacker.attack(target, param);
            } catch (NotSupportedException e) {
                System.out.println(e.toString());
            }
        }
        return this;
    }

    @Override
    public void teardown() {
        player = null;
        attacker = null;
        target = null;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
