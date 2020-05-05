package eod;

import eod.characters.Character;
import eod.exceptions.MoveInvalidException;
import eod.snapshots.BoardSnapshot;
import eod.snapshots.Snapshotted;

import java.awt.*;
import java.util.ArrayList;

public class Gameboard implements Snapshotted, GameObject {

    public static final int boardSize = 8;
    public static final int firstLine = 2, secondLine = 6;
    private Game game;
    private Character[][] board = new Character[boardSize][boardSize];

    public Gameboard(Game game) {
        this.game = game;
    }

    public Character getObjectOn(int x, int y) throws IllegalArgumentException {
        if(!hasObjectOn(x, y)) {
            throw new IllegalArgumentException("There's no character on ("+x+", "+y+").");
        }
        return board[x][y];
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

    public void removeCharacter(Character character) throws IllegalArgumentException {
        Point position = character.position;
        int x = position.x;
        int y = position.y;
        removeObject(x, y);
    }

    public void removeObject(int x, int y) throws IllegalArgumentException {
        if(x<0 || x >= boardSize || y < 0 || y >= boardSize) {
            throw new IllegalArgumentException("Trying to remove a character at ("+x+", "+y+").");
        }
        if(board[x][y] == null) {
            throw new IllegalArgumentException("There's nothing at ("+x+", "+y+") on the board, cannot remove.");
        }
        board[x][y] = null;
    }

    public ArrayList<Point> allEmptySpaces() {
        ArrayList<Point> points = new ArrayList<>();
        for(int i = 0;i < boardSize; i++) {
            for(int j = 0;j < boardSize;j++) {
                if(!hasObjectOn(i, j)) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }

    public ArrayList<Point> allEmptySpaces(Point at) {
        int iMin, iMax;
        if(at.x < firstLine) {
            iMin = 0;
            iMax = firstLine;
        } else if(at.x < secondLine) {
            iMin = firstLine;
            iMax = secondLine;
        } else {
            iMin = secondLine;
            iMax = boardSize;
        }

        ArrayList<Point> points = new ArrayList<>();
        for(int i = iMin;i < iMax;i++) {
            for(int j =0;j < boardSize;j++) {
                if(!hasObjectOn(i, j)) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }

    @Override
    public void teardown() {
        game = null;

        for(Character[] line:board) {
            for(Character character:line) {
                character.teardown();
            }
        }
        board = null;
    }

    @Override
    public BoardSnapshot snapshot() {
        return new BoardSnapshot(board);
    }
}
