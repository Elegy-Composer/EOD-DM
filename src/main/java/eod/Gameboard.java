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

    public boolean hasObjectOn(int x, int y) throws ArrayIndexOutOfBoundsException {
        if(x<0 || x>= boardSize || y<0 || y>= boardSize) {
            throw new ArrayIndexOutOfBoundsException("Trying to find an element at ("+x+", "+y+") on the board.");
        }
        return board[x][y]!=null;
    }

    public void moveElement(Point from, Point to) throws MoveInvalidException, ArrayIndexOutOfBoundsException {
        if(to.x < 0 || to.x >= boardSize || to.y < 0 || to.y >= boardSize) {
            throw new ArrayIndexOutOfBoundsException("Trying to move a character to ("+to.x+", "+to.y+").");
        }

        if(board[to.x][to.y] != null) {
            throw new MoveInvalidException("There's already a character on ("+to.x+", "+to.y+").");
        }

        board[to.x][to.y] = board[from.x][from.y];
        board[from.x][from.y] = null;
    }

    public void removeObject(int x, int y) throws IllegalArgumentException{
        if(x<0 || x >= boardSize || y < 0 || y >= boardSize) {
            throw new IllegalArgumentException("Trying to remove a character at ("+x+", "+y+").");
        }
        if(board[x][y] == null) {
            throw new IllegalArgumentException("There's nothing at ("+x+", "+y+") on the board, cannot remove.");
        }
        board[x][y] = null;
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
