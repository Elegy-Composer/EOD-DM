package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public class Summon implements Effect, GameObject {
    private WarObject object;
    private Point summonPoint;

    public Summon(WarObject object) {
        this.object = object;
    }

    public Summon on(Point point) {
        summonPoint = point;
        return this;
    }

    public Summon onOnePointOf(Player player, ArrayList<Point> points) {
        return on(askToSelectOneFrom(player, points));
    }

    public HandlerType desiredHandlerType() {
        return HandlerType.Owner;
    }

    public void action(EffectExecutor executor) throws WrongExecutorException {
        try {
            Player owner = (Player) executor;
            owner.summonObject(object, summonPoint);
        } catch (ClassCastException e) {
            throw new WrongExecutorException();
        }
    }

    @Override
    public void teardown() {
        object = null;
    }
}
