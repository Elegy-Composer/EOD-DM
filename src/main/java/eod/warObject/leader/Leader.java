package eod.warObject.leader;

import eod.Player;
import eod.card.abstraction.Card;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class Leader extends WarObject implements Damageable, CanAttack {
    private int max_hp, hp;
    private ArrayList<Card> specialOrder;
    protected ArrayList<Status> status;
    private CanAttack attacker;

    public Leader(Player player, int hp) {
        super(player);
        max_hp = hp;
        this.hp = max_hp;

        specialOrder = generateSpecialOrder();
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
        }
        else {
            this.hp += hp;
        }
    }

    public Leader enterArena() {
        ArrayList<Point> points = player.getBaseEmpty();
        position = player.selectPosition(points);
        player.summonObject(this, position);
        return this;
    }

    protected abstract ArrayList<Card> generateSpecialOrder();

    @Override
    public void attacked(CanAttack attacker, int hp) {
        this.attacker = attacker;
        damage(hp);
        this.attacker = null;
    }

    @Override
    public CanAttack getAttacker() {
        return attacker;
    }

    @Override
    public void damage(int hp) {
        this.hp -= hp;
        if(this.hp <= 0) {
            die();
        }
    }

    @Override
    public void die() {
        teardown();
        player.loseLeader();
    }

    public Card getSpecialOrder() {
        return specialOrder.remove(0);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public void teardown() {
        player = null;
    }
}
