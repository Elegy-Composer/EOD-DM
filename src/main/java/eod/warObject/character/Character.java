package eod.warObject.character;

import eod.*;
import eod.warObject.CanAttack;
import eod.warObject.Touchable;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Character extends WarObject implements Touchable, CanAttack {
    private ArrayList<Status> status = new ArrayList<>();
    protected int max_hp;
    protected int hp;
    public int attackRange;
    protected final Party party;

    public Character(Player player, int hp, int range, Party party) {
        super(player);
        this.attackRange = range;
        this.player = player;
        max_hp = hp;
        this.hp = max_hp;
        this.party = party;
    }

    @Override
    public boolean hasStatus(Status s) {
        return status.contains(s);
    }

    @Override
    public int getAttackRange() {
        return attackRange;
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
                Character target = gameboard.getObjectOn(p.x, p.y);
                target.damage(hp);
                target.addStatus(Status.ATTACKED);
            } catch (IllegalArgumentException e) {}
        }
    }

    @Override
    public void attack(Touchable target, int hp) {
        attack(new Touchable[] {target}, hp);
    }

    @Override
    public void attack(Touchable[] targets, int hp) {
        Arrays.stream(targets)
                .forEach(target -> {
                    target.damage(hp);
                    target.addStatus(Status.ATTACKED);
                });
    }

    @Override
    public void damage(int val) {
        hp -= val;
        if(hp <= 0) {
            die();
        }
    }

    private void die() {
        player.loseCharacter(this);
        teardown();
    }

    @Override
    public void teardown() {
        player = null;
    }

    public Party getParty() {
        return party;
    }
}
