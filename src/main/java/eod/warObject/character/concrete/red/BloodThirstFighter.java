package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.BloodThirstFighterSummon;
import eod.effect.RegionalAttack;
import eod.event.Event;
import eod.event.ObjectEnterEvent;
import eod.event.relay.EventReceiver;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class BloodThirstFighter extends Fighter {
    public BloodThirstFighter(Player player) {
        super(player, 4, 3, Party.RED);
        new OwnedAbilities(this);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new BloodThirstFighterSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "嗜血的戰鬥狂";
    }

    @Override
    public void attack() {
        super.attack();
        RegionalAttack a = RequestRegionalAttack(player, attack).from(this).to(getAttackRange(), 1);
        for(Damageable victim:a.getAffected()) {
            if(victim.getHp() <= 0) {
                addAttack(1);
                addHealth(1);
                attack();
                break;
            }
        }
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().get4Ways(position, param);
    }

    private class OwnedAbilities implements EventReceiver {
        private BloodThirstFighter holder;
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities(BloodThirstFighter holder) {
            this.holder = holder;
            canHandle = new ArrayList<>();
            canHandle.add(ObjectEnterEvent.class);
            holder.registerReceiver(this);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof ObjectEnterEvent) {
                ObjectEnterEvent e = (ObjectEnterEvent) event;
                if(e.getObject() == holder) {
                    holder.attack();
                    teardown();
                }
            }
        }

        @Override
        public void teardown() {
            holder.unregisterReceiver(this);
            holder = null;
            canHandle.clear();
            canHandle = null;
        }
    }
}
