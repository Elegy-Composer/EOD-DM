package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.exceptions.MoveInvalidException;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public class Move implements Effect, GameObject {
    private enum MoveMode {
        STEP, TELEPORT
    }
    private final MoveMode mode;
    private int step = -1; //use with MoveMode.STEP
    private Point selected; //use with MoveMode.TELEPORT

    private WarObject target;

    public Move() {
        // this declares a teleport move
        mode = MoveMode.TELEPORT;
    }

    public Move(int step) {
        this.step = step;
        mode = MoveMode.STEP;
    }

    public Move from(Player player, WarObject[] objects) {
        target = askToSelectOneFrom(player, objects);
        return this;
    }

    public Move from(WarObject object) {
        target = object;
        return this;
    }

    public Move to(Player player, ArrayList<Point> points) throws MoveInvalidException {
        if(mode != MoveMode.TELEPORT) {
            throw new MoveInvalidException("the method \"to\" is used only for teleport moves.");
        }
        selected = askToSelectOneFrom(player, points);
        return this;
    }

    public Move to(Point point) throws MoveInvalidException {
        if(mode != MoveMode.TELEPORT) {
            throw new MoveInvalidException("the method \"to\" is used only for teleport moves.");
        }
        selected = point;
        return this;
    }

    public WarObject getTarget() {
        return target;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        Player owner = castExecutor(executor);
        if (mode == MoveMode.STEP && step != -1) {
            owner.move(target, step);
        } else if (mode == MoveMode.TELEPORT && selected != null) {
            owner.moveObject(target, selected);
        }
    }

    @Override
    public HandlerType desiredHandlerType() {
        return HandlerType.Owner;
    }

    @Override
    public void teardown() {
        target = null;
    }
}
