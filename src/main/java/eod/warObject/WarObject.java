package eod.warObject;


import eod.GameObject;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.event.Event;
import eod.event.StatusAcquiredEvent;
import eod.event.relay.EventReceiver;
import eod.event.relay.EventSender;
import eod.event.relay.StatusHolder;
import eod.param.PointParam;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

//WarObject represented anything on the gameboard
// temporarily duplicated
public abstract class WarObject implements GameObject, EventReceiver, EventSender {
    public Point position;
    protected Player player;
    protected final Party party;
    ArrayList<Status> status;
    protected HashMap<Class<? extends Event>, ArrayList<EventReceiver>> receivers;

    public WarObject(Player player, Party party) {
        this.player = player;
        this.party = party;
        receivers = new HashMap<>();
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


    @Override
    public void registerReceiver(Class<? extends Event> supportedType, EventReceiver receiver) {
        receivers.putIfAbsent(supportedType, new ArrayList<>());
        receivers.get(supportedType).add(receiver);
    }

    @Override
    public void unregisterReceiver(Class<? extends Event> supportedType, EventReceiver receiver) {
        receivers.get(supportedType).remove(receiver);
    }

    @Override
    public StatusHolder[] getStatusHolders() {
        ArrayList<StatusHolder> holders = new ArrayList<>();
        for(ArrayList<EventReceiver> subReceivers:receivers.values()) {
            holders.addAll(subReceivers.stream().filter(receiver -> receiver instanceof StatusHolder).map(receiver -> (StatusHolder) receiver).collect(Collectors.toList()));
        }
        return holders.toArray(StatusHolder[]::new);
    }

    @Override
    public void send(GameObject sender, Event event) {
        receivers.get(event.getClass())
                .forEach(receiver -> receiver.onEventOccurred(sender, event));
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
        for(ArrayList<EventReceiver> subReceivers:receivers.values()) {
            subReceivers.forEach(EventReceiver::teardown);
        }
        receivers.clear();
    }

    public void transferTo(WarObject object) {
        player.transferObjectTo(this, object);
    }
}
