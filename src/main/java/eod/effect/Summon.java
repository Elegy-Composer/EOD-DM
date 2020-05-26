package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public class Summon implements Effect, GameObject {
    private WarObject object;
    private Point summonPoint;

    Summon(WarObject object) {
        this.object = object;
    }

    public Summon on(Point point) {
        summonPoint = point;
        return this;
    }

    public Summon onOnePointOf(Player player, ArrayList<Point> points) {
        return on(askToSelectOneFrom(player, points));
    }

    public Point getSummonPoint() {
        return summonPoint;
    }

    @Override
    public HandlerType desiredHandlerType() {
        return HandlerType.Owner;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        Player owner = castExecutor(executor);
        owner.summonObject(object, summonPoint);
    }

    @Override
    public void teardown() {
        object = null;
    }
}
