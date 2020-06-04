package eod.card.concrete.normal.transparent;

import eod.GameObject;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.event.Event;
import eod.event.RoundEndEvent;
import eod.event.TargetedEvent;
import eod.event.relay.EventReceiver;
import eod.param.DamageParam;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;

import java.util.ArrayList;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;
import static eod.effect.EffectFunctions.*;

public class FightBack extends NormalCard {
    public FightBack() {
        super(4);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        WarObject selected = player.selectObject(
                WarObject(player.getBoard())
                .which(Being(Damageable.class))
                .which(OwnedBy(player)).get()
        );
        selected.registerReceiver(new AttackDetect(selected));
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

        public AttackDetect(WarObject watching) {
            this.watching = watching;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof RoundEndEvent) {
                if(((RoundEndEvent) event).getEndedRound().getPlayer().isPlayerA() != watching.getPlayer().isPlayerA()) {
                    teardown();
                }
            }
            if(event instanceof TargetedEvent) {

                TargetedEvent e = (TargetedEvent) event;
                if(e.getTarget() == watching) {
                    if(watching.hasStatus(Status.NO_EFFECT)) {
                        return;
                    }
                    CanAttack attacker = e.getAttacker();
                    ((WarObject) attacker).getPlayer().tryToExecute(
                        Damage(new DamageParam(4), Effect.HandlerType.Rival).on((Damageable) attacker)
                    );
                }
                teardown();
            }
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return new ArrayList<Class<? extends Event>>(){{
                add(TargetedEvent.class);
                add(RoundEndEvent.class);
            }};
        }

        @Override
        public void teardown() {
            watching.unregisterReceiver(this);
            watching = null;
        }
    }
}
