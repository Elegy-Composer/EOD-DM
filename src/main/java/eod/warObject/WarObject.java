package eod.warObject;


import eod.GameObject;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.event.Event;
import eod.event.StatusAcquiredEvent;
import eod.event.relay.EventReceiver;
import eod.event.relay.EventSender;
import eod.param.PointParam;

import java.awt.*;
import java.util.ArrayList;

//WarObject represented anything on the gameboard
// temporarily duplicated
public abstract class WarObject implements GameObject, EventReceiver, EventSender {
    public Point position;
    protected Player player;
    protected final Party party;
    ArrayList<Status> status;
    protected ArrayList<Class<? extends Event>> canHandle;
    protected ArrayList<EventReceiver> receivers;

    public WarObject(Player player, Party party) {
        this.player = player;
        this.party = party;
        canHandle = new ArrayList<>();
        receivers = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public Point getPosition() {
        return position;
    }

    public Party getParty() {
        return party;
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

    public void addSupportedEventType(Class<? extends Event> eventType) {
        canHandle.add(eventType);
    }

    @Override
    public void registerReceiver(EventReceiver receiver) {
        receivers.add(receiver);
        canHandle.addAll(receiver.supportedEventTypes());
    }

    @Override
    public void unregisterReceiver(EventReceiver receiver) {
        receivers.remove(receiver);
    }

    @Override
    public void send(GameObject sender, Event event) {
        receivers.stream()
                .filter(receiver -> receiver.supportedEventTypes().contains(event.getClass()))
                .forEach(receiver -> receiver.onEventOccurred(sender, event));
    }

    @Override
    public ArrayList<Class<? extends Event>> supportedEventTypes() {
        return canHandle;
    }

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
        send(sender, event);
    }

    public abstract String getName();

    public void addStatus(Status s) {
        if(!hasStatus(s)) {
            player.sendUp(this, new StatusAcquiredEvent(this, s));
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
        receivers.forEach(EventReceiver::teardown);
        receivers.clear();
    }

    public void transferTo(WarObject object) {
        player.transferObjectTo(this, object);
    }
}
