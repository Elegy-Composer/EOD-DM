package eod;

import eod.event.DirectAttackEvent;
import eod.event.RegionalAttackEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Character implements WarObject, GameObject {
    private Player player;
    public boolean isTargeted;
    public Point position;
    public boolean isAttacked = false;
    private int max_hp = 30;
    private int hp;
    public int attackRange;
    protected final Party party;

    public Character(Player player, int x, int y, int range, Party party) {
        this.attackRange = range;
        this.player = player;
        position = new Point(x, y);
        hp = max_hp;
        this.party = party;
    }

    public Character(Player player, int x, int y, int hp, int range, Party party) {
        this.attackRange = range;
        this.player = player;
        position = new Point(x, y);
        max_hp = hp;
        this.hp = max_hp;
        this.party = party;
    }

    public Player getPlayer() {
        return player;
    }

    public void move(int steps) {
        for(int i = 0;i < steps;i++) {
            move();
        }
    }

    public void heal(int gain) {
        if(hp+gain >= max_hp) {
            hp = max_hp;
        } else {
            hp+=gain;
        }
    }

    public void attack(ArrayList<Point> targets, int hp) {
        attack(targets, hp, true, true);
    }

    public void attack(ArrayList<Point> targets, int hp, boolean allowCondition, boolean willSuccess) {
        Point[] targetArray = targets.toArray(new Point[0]);
        RegionalAttackEvent event = new RegionalAttackEvent(player, this
                , targetArray, hp, allowCondition, willSuccess);
        player.sendAttackEvent(event);
        Gameboard gameboard = player.getBoard();
        for(Point p:targetArray) {
            try {
                gameboard.getObjectOn(p.x, p.y).damage(hp);
            } catch (IllegalArgumentException e) { }
        }
    }

    public void attack(Character target, int hp) {
        attack(new Character[] {target}, hp);
    }

    public void attack(Character target, int hp, boolean allowCondition, boolean willSuccess) {
        attack(new Character[] {target}, hp, allowCondition, willSuccess);
    }

    public void attack(Character[] targets, int hp) {
        attack(targets, hp, true, true);
    }

    public void attack(Character[] targets, int hp, boolean allowCondition, boolean willSuccess) {
        DirectAttackEvent event = new DirectAttackEvent(player, this, targets, hp, allowCondition, willSuccess);
        player.sendAttackEvent(event);
        Arrays.stream(targets)
                .filter(character -> character.isTargeted)
                .forEach(character -> {
                    character.damage(hp);
                    character.isTargeted = false;
                });
    }

    public void damage(int val) {
        hp -= val;
        if(hp <= 0) {
            die();
        }
    }

    protected void move() {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        int x = position.x, y = position.y;
        Gameboard gameboard = player.getBoard();

        int toX = x-1;
        int toY = y;
        addPointIfEmpty(possibleMoves, toX, toY, gameboard);
        toX = x+1;
        addPointIfEmpty(possibleMoves, toX, toY, gameboard);
        toX = x;
        toY = y-1;
        addPointIfEmpty(possibleMoves, toX, toY, gameboard);
        toY = y+1;
        addPointIfEmpty(possibleMoves, toX, toY, gameboard);

        player.moveCharacter(this, player.selectPosition(possibleMoves));
    }

    private boolean addPointIfEmpty(ArrayList<Point> points, int x, int y, Gameboard gameboard) {
        try {
            if(!gameboard.hasObjectOn(x, y)) {
                points.add(new Point(x, y));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void moveTo(Point point) {
        position.move(point.x, point.y);
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
