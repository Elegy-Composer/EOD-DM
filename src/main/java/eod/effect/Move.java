package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.Character;

public class Move implements Effect, GameObject {
    private int step;
    private Player player;

    public Move(Player player, int step) {
        this.step = step;
        this.player = player;
    }

    public void from(Character[] characters) {
        Character selected = askToSelect(player, characters);
        selected.move(step);
    }

    @Override
    public void teardown() {
        player = null;
    }
}
