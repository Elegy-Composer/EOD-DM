package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.param.AttackParam;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

import java.util.ArrayList;

public abstract class Attack implements Effect, GameObject {
    protected Player player;
    protected AttackParam param;
    protected CanAttack attacker;
    protected ArrayList<Damageable> affected;

    public Attack(Player player, int hp) {
        this.player = player;
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
        player = null;
        attacker = null;
        affected.clear();
        param = null;
    }
}
