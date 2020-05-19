package eod.card.concrete.normal;

import eod.GameObject;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.RoundEndEvent;
import eod.event.TargetedEvent;
import eod.event.listener.EventListener;
import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestDirectAttack;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class FightBack extends NormalCard {
    public FightBack() {
        super(4);
    }

    @Override
    public void applyEffect() {
        player.registerListener(
                new AttackDetect((Damageable) player.selectObject(
                    WarObject(player.getBoard())
                            .which(Being(Damageable.class))
                            .which(OwnedBy(player)).get())
                )
        );
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

    public class AttackDetect implements EventListener, GameObject {
        private Damageable watching;
        private ArrayList<Class<? extends Event>> canHandle;

        public AttackDetect(Damageable watching) {
            this.watching = watching;
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
                    WarObject attacker = (WarObject) e.getAttacker();
                    CanAttack w = (CanAttack) watching;
                    try {
                        if (w.getAttackRange().contains(attacker.position)) {
                            RequestDirectAttack(player, w.getAttack()).from((WarObject) w).toAll(new Damageable[] {(Damageable) attacker});
                        }
                    } catch (NotSupportedException exception) {
                        System.out.println("The object "+((WarObject) w).getName()+" doesn't have its attack range or direct attack method.");
                    }
                }
            }
        }

        @Override
        public void teardown() {
            canHandle.clear();
            watching = null;
        }
    }
}
