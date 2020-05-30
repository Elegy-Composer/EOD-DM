package eod.card.concrete.normal.blue;

import eod.GameObject;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.RoundEndEvent;
import eod.event.TargetedEvent;
import eod.event.relay.EventReceiver;
import eod.param.DamageParam;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.util.ArrayList;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;
import static eod.effect.EffectFunctions.*;

public class SupportAttack extends NormalCard {
    public SupportAttack() {
        super(2);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        new EnemyAttack((Character) player.selectObject(
                WarObject(player.getBoard())
                .which(Being(Character.class))
                .which(OwnedBy(player)).get()
        ));
    }

    @Override
    public Card copy() {
        Card c = new SupportAttack();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "支援打擊";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }

    public class EnemyAttack implements EventReceiver, GameObject {
        private Character holder;

        public EnemyAttack(Character holder) {
            this.holder = holder;
            holder.registerReceiver(RoundEndEvent.class, this);
            holder.registerReceiver(TargetedEvent.class, this);
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof RoundEndEvent) {
                RoundEndEvent e = (RoundEndEvent) event;
                if(e.getEndedRound().getPlayer().isPlayerA() != holder.getPlayer().isPlayerA()) {
                    teardown();
                }
            }
            if(event instanceof TargetedEvent) {
                if(holder.hasStatus(Status.NO_EFFECT)) {
                    return;
                }
                TargetedEvent e = (TargetedEvent) event;
                if(e.getTarget() != holder && ((WarObject)e.getAttacker()).getPlayer().isPlayerA() != holder.getPlayer().isPlayerA()) {
                    player.tryToExecute(
                        Damage(new DamageParam(2), Effect.HandlerType.Rival).on((Damageable) e.getAttacker())
                    );
                    teardown();
                }
            }
        }

        @Override
        public void teardown() {
            holder.unregisterReceiver(RoundEndEvent.class, this);
            holder.unregisterReceiver(TargetedEvent.class, this);
            holder = null;
        }
    }
}
