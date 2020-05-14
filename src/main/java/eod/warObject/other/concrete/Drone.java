package eod.warObject.other.concrete;

import eod.Player;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.other.abstraction.Machine;

import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class Drone extends Machine implements CanAttack, Damageable {
    private int max_hp = 2, hp = max_hp, attack = 3;
    private ArrayList<Status> status = new ArrayList<>();

    public Drone(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "無人機";
    }

    public void attacked(CanAttack attacker, int hp) {
        damage(hp);
    }

    @Override
    public void damage(int hp) {
        this.hp -= hp;
        if(this.hp <= 0) {
            die();
        }
    }

    @Override
    public void addStatus(Status s) {
        if(!status.contains(s)) {
            status.add(s);
        }
    }

    @Override
    public boolean hasStatus(Status s) {
        return status.contains(s);
    }

    @Override
    public void removeStatus(Status s) {
        status.remove(s);
    }

    @Override
    public void heal(int hp) {
        if(this.hp + hp >= max_hp) {
            this.hp = max_hp;
        }
        else {
            this.hp += hp;
        }
    }

    @Override
    public void die() {
        player.loseObject(this);
        teardown();
    }

    @Override
    public CanAttack getAttacker() {
        return null;
    }

    @Override
    public void attack() {
        int plusAttack = WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Drone.class)).which(InRangeOf(this)).get().length*2;
        attack += plusAttack;
        RequestRegionalAttack(player, attack).from(this).to(WarObject(player.getBoard())
                .which(OwnedBy(player.rival()))
                .which(Being(Damageable.class))
                .which(InRangeOf(this)).get());
    }

    @Override
    public int getAttackRange() {
        return 1;
    }

    @Override
    public void teardown() {
        super.teardown();
        status = null;
    }
}
