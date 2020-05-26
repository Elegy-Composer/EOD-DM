package eod.card.concrete.normal;

import eod.GameObject;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Damage;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.TargetedEvent;
import eod.event.relay.EventReceiver;
import eod.param.DamageParam;
import eod.warObject.Damageable;
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
        private ArrayList<Class<? extends Event>> canHandle;

        public EnemyAttack(Character holder) {
            this.holder = holder;
            canHandle = new ArrayList<>();
            canHandle.add(ObjectDeadEvent.class);
            canHandle.add(TargetedEvent.class);
            holder.registerReceiver(this);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof ObjectDeadEvent) {
                ObjectDeadEvent e = (ObjectDeadEvent) event;
                if(e.getDeadObject() == holder) {
                    teardown();
                }
            }
            if(event instanceof TargetedEvent) {
                TargetedEvent e = (TargetedEvent) event;
                if(e.getTarget() != holder) {
                    player.tryToExecute(
                        Damage(new DamageParam(2), Effect.HandlerType.Rival).on((Damageable) e.getAttacker())
                    );
                    teardown();
                }
            }
        }

        @Override
        public void teardown() {
            holder.unregisterReceiver(this);
            canHandle.clear();
            canHandle = null;
            holder = null;
        }
    }
}
