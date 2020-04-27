package eod;

import eod.snapshots.BoardSnapshot;
import eod.snapshots.Snapshotted;

public class Gameboard implements Snapshotted, GameObject {

    private Game game;
    private static Character[][] board = new Character[8][8];

    public Gameboard(Game game) {
        this.game = game;
        board[0][0].isAttacked = true;
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
