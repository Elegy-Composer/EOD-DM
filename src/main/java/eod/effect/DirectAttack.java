package eod.effect;

import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

public class DirectAttack extends Attack {
    // This class should be used only in direct attacks.
    // If there's a ranged attack, use RegionalAttack.
    private Damageable target;

    public DirectAttack(Player player, int hp) {
        super(player, hp);
    }

    public DirectAttack from(WarObject[] objects) {
        attacker = (CanAttack) askToSelectOneFrom(objects);
        return this;
    }

    public DirectAttack from(WarObject object) {
        attacker = (CanAttack) object;
        return this;
    }

    public DirectAttack realDamage() {
        param.realDamage = true;
        return this;
    }

    public DirectAttack to(WarObject[] objects) {
        target = (Damageable) askToSelectOneFrom(objects);
        try {
            affected.addAll(attacker.attack(target, param));
        } catch (NotSupportedException e) {
            System.out.println(e.toString());
        }
        return this;
    }

    public DirectAttack toAll(Damageable[] targets) {
        for(Damageable target:targets) {
            try {
                affected.addAll(attacker.attack(target, param));
            } catch (NotSupportedException e) {
                System.out.println(e.toString());
            }
        }
        return this;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
