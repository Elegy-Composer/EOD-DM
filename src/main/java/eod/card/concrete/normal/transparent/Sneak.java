package eod.card.concrete.normal.transparent;

import eod.GameObject;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.event.AttackEvent;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.relay.EventReceiver;
import eod.specifier.Accessing;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.assaulter.Assassin;

import java.util.ArrayList;
import java.util.Arrays;

import static eod.effect.EffectFunctions.GiveStatus;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;
import static eod.warObject.Status.NO_EFFECT;

public class Sneak extends NormalCard {

    public Sneak() {
        super(2);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        Accessing ownedObjects = WarObject(player.getBoard()).which(OwnedBy(player));
        WarObject[] assassins = ownedObjects.which(Being(Assassin.class)).get();
        boolean afterEffect = assassins.length >= 3;
        executor.tryToExecute(
            GiveStatus(Status.SNEAK, Effect.HandlerType.Owner)
                    .to(player.selectObject(ownedObjects.which(Being(Character.class)).get()))
        );


        if(afterEffect) {
            Arrays.stream(assassins)
                .map(object -> (Assassin) object)
                .forEach(assassin -> assassin.registerReceiver(new NextDamageDouble(assassin)));
        }
    }


    @Override
    public Card copy() {
        Card c = new Sneak();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "潛行";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }

    private class NextDamageDouble implements EventReceiver, GameObject {
        private Assassin assassin;

        public NextDamageDouble(Assassin assassin) {
            this.assassin = assassin;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof AttackEvent) {
                AttackEvent e = (AttackEvent) event;
                if(e.getAttacker() == assassin) {
                    if (!assassin.hasStatus(NO_EFFECT)) {
                        e.param.hp *= 2;
                    }
                    teardown();
                }
            }
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return new ArrayList<Class<? extends Event>>() {{
                add(AttackEvent.class);
            }};
        }

        @Override
        public void teardown() {
            assassin.unregisterReceiver(this);
            assassin = null;
        }
    }
}
