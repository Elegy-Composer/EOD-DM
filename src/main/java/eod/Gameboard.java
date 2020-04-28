package eod;

import eod.snapshots.BoardSnapshot;
import eod.snapshots.Snapshotted;

import java.util.ArrayList;
import java.util.Collections;

public class Gameboard implements Snapshotted, GameObject {

    private Game game;
    private Character[][] board = new Character[8][8];

    public Gameboard(Game game) {
        this.game = game;
        board[0][0].isAttacked = true;
    }

    public Character[] selectCharacter(Player player, int num) {
        ArrayList<Character> canSelect = new ArrayList<>();
        for(int i = 0;i < board.length;i++) {
            for(int j = 0;j < board[i].length;j++) {
                if(board[i][j] == null) {
                    continue;
                }
                if(board[i][j].getPlayer() == player) {
                    canSelect.add(board[i][j]);
                    // TODO: let the characters glow on the board, indicating that the player can select them.
                }
            }
        }
        return selectCharacter(canSelect, num);
    }

    public Character[] selectCharacter(ArrayList<Character> canSelect, int num) {
        // TODO: let the player select characters
        Collections.shuffle(canSelect);
        return canSelect.subList(0, num).stream().toArray(Character[]::new);
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
