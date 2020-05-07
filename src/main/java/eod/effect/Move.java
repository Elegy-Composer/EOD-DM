package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.warObject.WarObject;
import eod.warObject.character.Character;
import eod.exceptions.MoveInvalidException;

import java.awt.*;
import java.util.ArrayList;

public class Move implements Effect, GameObject {
    private enum MoveMode {
        STEP, TELEPORT
    }
    private final MoveMode mode;
    private int step;
    private Player player;
    private WarObject target;

    public Move(Player player) {
        // this declares a teleport move
        mode = MoveMode.TELEPORT;
        this.player = player;
    }

    public Move(Player player, int step) {
        this.step = step;
        mode = MoveMode.STEP;
        this.player = player;
    }

    public Move from(WarObject[] objects) {
        target = askToSelectFrom(objects);
        if(mode==MoveMode.STEP) {
            target.move(step);
        }
        return this;
    }

    public Move to(ArrayList<Point> points) throws MoveInvalidException {
        if(mode != MoveMode.TELEPORT) {
            throw new MoveInvalidException("the method \"to\" is used only for teleport moves.");
        }
        Point selected = askToSelectFrom(points);
        target.moveTo(selected);
        return this;
    }

    public WarObject getTarget() {
        return target;
    }

    @Override
    public void teardown() {
        player = null;
        target = null;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
