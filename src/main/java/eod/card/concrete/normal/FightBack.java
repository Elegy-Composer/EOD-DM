package eod.card.concrete.normal;

import eod.GameObject;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.RoundEndEvent;
import eod.event.TargetedEvent;
import eod.event.relay.EventReceiver;
import eod.param.DamageParam;
import eod.warObject.Damageable;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.util.ArrayList;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class FightBack extends NormalCard {
    public FightBack() {
        super(4);
    }

    @Override
    public void applyEffect() {
        new AttackDetect(player.selectObject(
                WarObject(player.getBoard())
                .which(Being(Character.class))
                .which(OwnedBy(player)).get()
        ));
    }

    @Override
    public Card copy() {
        Card c = new FightBack();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "反擊";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }

    public class AttackDetect implements EventReceiver, GameObject {
        private WarObject watching;
        private ArrayList<Class<? extends Event>> canHandle;

        public AttackDetect(WarObject watching) {
            this.watching = watching;
            watching.registerReceiver(this);
            canHandle = new ArrayList<>();
            canHandle.add(ObjectDeadEvent.class);
            canHandle.add(TargetedEvent.class);
            canHandle.add(RoundEndEvent.class);
        }
        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof ObjectDeadEvent) {
                if(((ObjectDeadEvent) event).getDeadObject() == watching) {
                    teardown();
                }
            }
            if(event instanceof RoundEndEvent) {
                if(((RoundEndEvent) event).getEndedRound().getPlayer().isPlayerA() == player.rival().isPlayerA()) {
                    teardown();
                }
            }
            if(event instanceof TargetedEvent) {
                TargetedEvent e = (TargetedEvent) event;
                if(e.getTarget() == watching) {
                    Damageable attacker = (Damageable) e.getAttacker();
                    DamageParam param = new DamageParam(4);
                    attacker.damage(param);
                }
                teardown();
            }
        }

        @Override
        public void teardown() {
            canHandle.clear();
            watching.unregisterReceiver(this);
            watching = null;
        }
    }
}
