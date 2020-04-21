package eod.snapshots;

import eod.Player;

public class GameSnapshot {
    Player A, B;
    BoardSnapshot boardSnapshot;

    public GameSnapshot(Player A, Player B, BoardSnapshot boardSnapshot){
        this.A = A;
        this.B = B;
        this.boardSnapshot = boardSnapshot;
    }
}
