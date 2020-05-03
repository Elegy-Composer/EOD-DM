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

    public Move from(Character[] characters) {
        Character selected = askToSelectFrom(characters);
        selected.move(step);
        //TODO: This is a temporary fix
        // attack should later be cataloged into Regional attach and personal attack
        selected.isTargeted = false;

        return this;
    }

    @Override
    public void teardown() {
        player = null;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
