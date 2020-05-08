package eod.warObject;


import eod.GameObject;
import eod.Gameboard;
import eod.Player;

import java.awt.*;
import java.util.ArrayList;

//WarObject represented anything on the gameboard
public abstract class WarObject implements GameObject {
    public Point position;
    protected Player player;

    public WarObject(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Point getPosition() {
        return position;
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

        player.moveObject(this, player.selectPosition(possibleMoves));
    }

    public void move(int steps) {
        for(int i = 0;i < steps;i++) {
            move();
        }
    }

    public void moveTo(Point point) {
        player.moveObject(this, point);
    }

    public void updatePosition(Point point) {
        position.move(point.x, point.y);
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
}
