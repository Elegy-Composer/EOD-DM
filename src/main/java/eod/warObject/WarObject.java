package eod.warObject;


import eod.GameObject;
import eod.Gameboard;
import eod.Player;
import eod.event.Event;
import eod.event.listener.EventListener;
import eod.param.PointParam;

import java.awt.*;
import java.util.ArrayList;

//WarObject represented anything on the gameboard
// temporarily duplicated
public abstract class WarObject implements GameObject, EventListener {
    public Point position;
    protected Player player;
    public ArrayList<Class<? extends Event>> canHandle;
    ArrayList<Status> status;

    public WarObject(Player player) {
        this.player = player;
        canHandle = new ArrayList<>();
        status = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public Point getPosition() {
        return position;
    }

    protected void move() {
        PointParam param = new PointParam();
        param.range = 1;
        param.emptySpace = true;
        ArrayList<Point> possibleMoves = player.getBoard().getSurrounding(position, param);
        moveTo(player.selectPosition(possibleMoves));
    }

    public void move(int steps) {
        for(int i = 0;i < steps;i++) {
            move();
        }
    }

    public void moveTo(Point point) {
        player.moveObject(this, point);
    }

    public void updatePosition(Point point) {
        position.move(point.x, point.y);
    }

    private boolean addPointIfEmpty(ArrayList<Point> points, int x, int y, Gameboard gameboard) {
        try {
            if(!gameboard.hasObjectOn(x, y)) {
                points.add(new Point(x, y));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ArrayList<Class<? extends Event>> supportedEventTypes() {
        return canHandle;
    }

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
    }

    public abstract String getName();

    public void addStatus(Status s) {
        if(!hasStatus(s)) {
            status.add(s);
        }
    }
    public boolean hasStatus(Status s) {
        return status.contains(s);
    }

    public void removeStatus(Status s) {
        status.remove(s);
    }

    @Override
    public void teardown() {
        player = null;
        canHandle.clear();
    }
}
