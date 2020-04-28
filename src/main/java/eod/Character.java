package eod;

import java.awt.*;
import java.util.ArrayList;

public class Character implements WarObject {
    private Player player;
    public boolean isTargeted;
    public Point position;
    public boolean isAttacked = false;

    public Character(Player player, int x, int y) {
        this.player = player;
        position = new Point(x, y);
    }

    public Player getPlayer() {
        return player;
    }

    public void move(int steps) {
        for(int i = 0;i < steps;i++) {
            move();
        }
    }

    protected void move() {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        int x = position.x, y = position.y;
        int toX = x-1, toY = y;
        Gameboard gameboard = player.getBoard();

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
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void moveTo(Point point) {
        position.move(point.x, point.y);
    }
}
