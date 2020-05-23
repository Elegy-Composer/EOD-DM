package eod.card.concrete.normal;

import eod.GameObject;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
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

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;
import static eod.effect.EffectFunctions.GiveStatus;

public class Sneak extends NormalCard{
    public Sneak() {
        super(2);
    }
    @Override
    public void applyEffect() {
        Accessing ownedObjects = WarObject(player.getBoard()).which(OwnedBy(player));
        WarObject[] assassins = ownedObjects.which(Being(Assassin.class)).get();
        boolean afterEffect = assassins.length >= 3;
        GiveStatus(player, Status.SNEAK).to(player.selectObject(ownedObjects.which(Being(Character.class)).get()));

        if(afterEffect) {
            Arrays.stream(assassins)
                .map(object -> (Assassin) object)
                .forEach(NextDamageDouble::new);
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

    public class NextDamageDouble implements EventReceiver, GameObject {
        private Assassin assassin;
        private ArrayList<Class<? extends Event>> canHandle;

        public NextDamageDouble(Assassin assassin) {
            this.assassin = assassin;
            canHandle = new ArrayList<>();
            canHandle.add(ObjectDeadEvent.class);
            canHandle.add(AttackEvent.class);
            assassin.registerReceiver(this);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof AttackEvent) {
                AttackEvent e = (AttackEvent) event;
                if(e.getAttacker() == assassin) {
                    e.param.hp *= 2;
                    teardown();
                }
            }
            if(event instanceof ObjectDeadEvent) {
                if(((ObjectDeadEvent) event).getDeadObject() == assassin) {
                    teardown();
                }
            }
        }

        @Override
        public void teardown() {
            assassin.unregisterReceiver(this);
            assassin = null;
            canHandle.clear();
            canHandle = null;
        }
    }
}
