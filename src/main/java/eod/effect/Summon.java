package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.event.ObjectEnterEvent;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public class Summon implements Effect, GameObject {
    private Player player;
    private WarObject object;

    public Summon(Player player, WarObject object) {
        this.player = player;
        this.object = object;
    }

    public Point on(Point point) {
        player.summonObject(object, point);
        return point;
    }

    public Point from(ArrayList<Point> points) {
        return on(askToSelectOneFrom(points));
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void teardown() {
        player = null;
        object = null;
    }
}
