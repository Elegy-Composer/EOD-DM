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

    private void move() {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        int x = position.x, y = position.y;
        Gameboard gameboard = player.getBoard();

        if(x!=0 && !gameboard.hasObjectOn(x-1, y)) {
            possibleMoves.add(new Point(x-1, y));
        }
        if(x!=Gameboard.boardSize-1 && !gameboard.hasObjectOn(x+1, y)) {
            possibleMoves.add(new Point(x+1, y));
        }
        if(y!=0 && !gameboard.hasObjectOn(x, y-1)) {
            possibleMoves.add(new Point(x, y-1));
        }
        if(y!=Gameboard.boardSize-1 && !gameboard.hasObjectOn(x, y+1)) {
            possibleMoves.add(new Point(x, y+1));
        }

        player.moveCharacter(this, player.selectPosition(possibleMoves));
    }

    public void moveTo(Point point) {
        position.move(point.x, point.y);
    }
}
