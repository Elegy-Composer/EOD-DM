package eod.warObject.other.concrete;

import eod.Gameboard;
import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.param.AttackParam;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.other.abstraction.Machine;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class SecureGuardBot extends Machine implements CanAttack, Damageable {
    // TODO: add the cost (4) and its summon card.
    int max_hp = 4, hp = max_hp, attack = 4;
    CanAttack attacker;
    ArrayList<Status> status;
    public SecureGuardBot(Player player) {
        super(player);
        status = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "維安警備機械";
    }

    @Override
    public void damage(int hp) {
        this.hp -= hp;
        if(this.hp <= 0) {
            die();
        }
    }

    @Override
    public void attacked(CanAttack attacker, int hp) {
        this.attacker = attacker;
        damage(hp);
        this.attacker = null;
    }

    @Override
    public void addStatus(Status s) {
        if(!hasStatus(s)) {
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
        } else {
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
        return attacker;
    }

    @Override
    public void attack(){
        RequestRegionalAttack(player, attack).from(this).to(getAttackRange());
    }

    @Override
    public void attack(ArrayList<Point> targets, AttackParam param) {
        int hp = param.hp;
        Gameboard gameboard = player.getBoard();
        for(Point p:targets) {
            try {
                Damageable target = gameboard.getObjectOn(p.x, p.y);
                if(param.realDamage) {
                    target.damage(hp);
                } else {
                    target.attacked(this, hp);
                }
                target.addStatus(Status.ATTACKED);
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBoard().getSurrounding(this.position, 1);
    }
}
