package eod.effect;

import eod.GameObject;
import eod.param.AttackParam;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;

import java.util.ArrayList;

public abstract class Attack implements Effect, GameObject {

    protected AttackParam param;
    protected CanAttack attacker;
    protected ArrayList<Damageable> affected;

    public Attack(int hp) {
        param = new AttackParam();
        param.hp = hp;
        affected = new ArrayList<>();
    }

    public CanAttack attacker() {
        return attacker;
    }

    public ArrayList<Damageable> getAffected() {
        return affected;
    }

    @Override
    public void teardown() {
        attacker = null;
        affected.clear();
        param = null;
    }
}
