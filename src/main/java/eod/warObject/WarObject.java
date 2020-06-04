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
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

//WarObject represented anything on the gameboard
// temporarily duplicated
public abstract class WarObject implements GameObject, EventReceiver, EventSender {
    public Point position;
    protected Player player;
    protected final Party party;
    ArrayList<Status> status;
    private ArrayList<StatusHolder> statusHolders;
    protected HashMap<Class<? extends Event>, ArrayList<EventReceiver>> receivers;

    public WarObject(Player player, Party party) {
        this.player = player;
        this.party = party;
        statusHolders = new ArrayList<>();
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


    public void registerStatusHolder(StatusHolder holder) {
        statusHolders.add(holder);
    }

    public void unregisterStatusHolder(StatusHolder holder) {
        statusHolders.remove(holder);

    }

    @Override
    public void registerReceiver(EventReceiver receiver) {
        for(Class<? extends Event> eventType:receiver.supportedEventTypes()) {
            receivers.putIfAbsent(eventType, new ArrayList<>());
            receivers.get(eventType).add(receiver);
        }
    }

    @Override
    public void unregisterReceiver(EventReceiver receiver) {
        for(Class<? extends Event> eventType:receiver.supportedEventTypes()) {
            receivers.get(eventType).remove(receiver);
        }
    }

    public ArrayList<StatusHolder> getStatusHolders() {
        return statusHolders;
    }

    @Override
    public void send(GameObject sender, Event event) {
        receivers.get(event.getClass())
                .forEach(receiver -> receiver.onEventOccurred(sender, event));
    }

    @Override
    public ArrayList<Class<? extends Event>> supportedEventTypes() {
        return new ArrayList<Class<? extends Event>>() {{
            addAll(Arrays.stream(Event.allEvents).collect(Collectors.toList()));
        }};
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
