package eod.warObject.character.abstraction;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Character extends WarObject implements Damageable, CanAttack {
    private ArrayList<Status> status = new ArrayList<>();
    protected int max_hp;
    protected int hp;
    protected int attack;
    public int attackRange;
    protected final Party party;
    private CanAttack attacker;

    public Character(Player player, int hp, int attack, int range, Party party) {
        super(player);
        this.attackRange = range;
        this.player = player;
        max_hp = hp;
        this.hp = max_hp;
        this.attack = attack;
        this.party = party;
    }

    @Override
    public boolean hasStatus(Status s) {
        return status.contains(s);
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBoard().getSurroundingEmpty(position, attackRange);
    }

    @Override
    public void addStatus(Status s) {
        if(!hasStatus(s)) {
            status.add(s);
        }
    }

    @Override
    public void removeStatus(Status s) {
        status.remove(s);
    }

    @Override
    public void heal(int gain) {
        if(hp+gain >= max_hp) {
            hp = max_hp;
        } else {
            hp+=gain;
        }
    }

    @Override
    public void attack(ArrayList<Point> targets, int hp) {
        Gameboard gameboard = player.getBoard();
        for(Point p:targets) {
            try {
                Damageable target = gameboard.getObjectOn(p.x, p.y);
                target.attacked(this, hp);
                target.addStatus(Status.ATTACKED);
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public void attack(Damageable target, int hp) {
        attack(new Damageable[] {target}, hp);
    }

    @Override
    public void attack(Damageable[] targets, int hp) {
        Arrays.stream(targets)
                .forEach(target -> {
                    target.attacked(this, hp);
                    target.addStatus(Status.ATTACKED);
                });
    }

    @Override
    public void realAttack(ArrayList<Point> targets, int hp) {
        Gameboard gameboard = player.getBoard();
        for(Point p:targets) {
            try {
                Damageable target = gameboard.getObjectOn(p.x, p.y);
                target.damage(hp);
                target.addStatus(Status.ATTACKED);
            } catch(IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public void damage(int val) {
        hp -= val;
        if(hp <= 0) {
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
    public CanAttack getAttacker() {
        return attacker;
    }

    @Override
    public void die() {
        player.loseObject(this);
        teardown();
    }

    public Party getParty() {
        return party;
    }
}
