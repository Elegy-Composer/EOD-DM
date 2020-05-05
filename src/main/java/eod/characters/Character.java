package eod.characters;

import eod.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Character implements WarObject, GameObject {
    protected Player player;
    public Point position;
    public ArrayList<Status> status = new ArrayList<>();
    protected int max_hp;
    protected int hp;
    public int attackRange;
    protected final Party party;

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
        ArrayList<Character> targetCandidate = new ArrayList<>();
        Gameboard gameboard = player.getBoard();
        for(Point p:targetArray) {
            try {
                Character candidate = gameboard.getObjectOn(p.x, p.y);
                targetCandidate.add(candidate);
                candidate.status.add(Status.TARGETED);
            } catch (IllegalArgumentException e) {}
        }

        for(Point p:targetArray) {
            try {
                Character target = gameboard.getObjectOn(p.x, p.y);
                targetCandidate.remove(target);
                target.damage(hp);
                target.status.add(Status.ATTACKED);
                target.status.remove(Status.TARGETED);
            } catch (IllegalArgumentException e) { }
        }
        for(Character survivor:targetCandidate) {
            survivor.status.remove(Status.TARGETED);
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
        Arrays.stream(targets)
                .filter(character -> character.status.contains(Status.TARGETED))
                .forEach(character -> {
                    character.damage(hp);
                    character.status.add(Status.ATTACKED);
                    character.status.remove(Status.TARGETED);
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
        player.moveCharacter(this, point);
    }

    public void updatePosition(Point point) {
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
