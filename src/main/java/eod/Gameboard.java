package eod;

import eod.snapshots.BoardSnapshot;

public class Gameboard implements Snapshotted{

    private Game game;
    private static Character[][] board = new Character[8][8];

    public Gameboard(Game game) {
        this.game = game;
        board[0][0].isAttacked = true;
    }

    @Override
    public BoardSnapshot snapshot() {
        return new BoardSnapshot(board);
    }
}
