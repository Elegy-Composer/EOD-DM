package eod.card.concrete.normal;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.event.AttackEvent;
import eod.event.Event;
import eod.event.RoundEndEvent;
import eod.event.listener.EventListener;

import java.util.ArrayList;

public class GrandFeast extends NormalCard implements EventListener {
    ArrayList<Class<? extends Event>> canHandle;
    public GrandFeast() {
        canHandle = new ArrayList<>();
        canHandle.add(RoundEndEvent.class);
        canHandle.add(AttackEvent.class);
    }

    @Override
    public void applyEffect() {
        player.registerListener(this);
    }

    @Override
    public Card copy() {
        Card c = new GrandFeast();
        c.setPlayer(player);
        return c;
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String getName() {
        return "盛宴開始";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }

    @Override
    public ArrayList<Class<? extends Event>> supportedEventTypes() {
        return canHandle;
    }

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
        if (event instanceof AttackEvent) {
            AttackEvent e = (AttackEvent) event;
            if(e.getSender().isPlayerA() == player.isPlayerA()) {
                e.param.hp *= 2;
            }
        }
        else if (event instanceof RoundEndEvent) {
            player.unregisterListener(this);
            teardown();
        }
    }

    @Override
    public void teardown() {
        super.teardown();
        canHandle.clear();
        canHandle = null;
    }
}
