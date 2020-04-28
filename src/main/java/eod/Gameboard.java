package eod;

import eod.exceptions.MoveInvalidException;
import eod.snapshots.BoardSnapshot;
import eod.snapshots.Snapshotted;

import java.awt.*;

public class Gameboard implements Snapshotted, GameObject {

    public static final int boardSize = 8;
    private Game game;
    private Character[][] board = new Character[boardSize][boardSize];

    public Gameboard(Game game) {
        this.game = game;
        board[0][0].isAttacked = true;
    }

    public boolean hasObjectOn(int x, int y) throws ArrayIndexOutOfBoundsException{
        if(x<0 || x>= boardSize || y<0 || y>= boardSize) {
            throw new ArrayIndexOutOfBoundsException("Trying to find an element at ("+x+", "+y+") on the board");
        }
        return board[x][y]!=null;
    }

    public void moveElement(Point from, Point to) throws MoveInvalidException {
        if(board[to.x][to.y] != null) {
            throw new MoveInvalidException("There's already a character on "+to.x+", "+to.y);
        }
        Character temp = board[from.x][from.y];
        board[from.x][from.y] = null;
        board[to.x][to.y] = temp;
    }

    @Override
    public void teardown() {
        //TODO: finish teardown
    }

    @Override
    public BoardSnapshot snapshot() {
        return new BoardSnapshot(board);
    }
}
