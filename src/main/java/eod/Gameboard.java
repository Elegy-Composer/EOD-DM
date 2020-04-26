package eod;

import eod.snapshots.BoardSnapshot;

public class Gameboard {

    private static Character[][] board = new Character[8][8];

    public Gameboard() {
        board[0][0].isAttacked = true;
    }

    public static BoardSnapshot getSnapshot() {
        return new BoardSnapshot(board);
    }
}
