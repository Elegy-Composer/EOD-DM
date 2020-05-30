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
        new AttackDetect(player.selectObject(
            WarObject(player.getBoard())
                    .which(Being(Damageable.class))
                    .which(OwnedBy(player)).get())
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

    public class AttackDetect implements EventReceiver, GameObject {
        private WarObject watching;

        public AttackDetect(WarObject watching) {
            this.watching = watching;
            watching.registerReceiver(TargetedEvent.class, this);
            watching.registerReceiver(RoundEndEvent.class, this);
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof RoundEndEvent) {
                if(((RoundEndEvent) event).getEndedRound().getPlayer().isPlayerA() != watching.getPlayer().isPlayerA()) {
                    teardown();
                }
            }
            if(event instanceof TargetedEvent) {
                if(watching.hasStatus(Status.NO_EFFECT)) {
                    return;
                }
                TargetedEvent e = (TargetedEvent) event;
                if(e.getTarget() == watching) {
                    CanAttack attacker = e.getAttacker();
                    ((WarObject) attacker).getPlayer().tryToExecute(
                        Damage(new DamageParam(4), Effect.HandlerType.Rival).on((Damageable) attacker)
                    );
                }
                teardown();
            }
        }

        @Override
        public void teardown() {
            watching.unregisterReceiver(TargetedEvent.class, this);
            watching.unregisterReceiver(RoundEndEvent.class, this);
            watching = null;
        }
    }
}
