package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.red.BloodThirstFighterSummon;
import eod.effect.EffectExecutor;
import eod.effect.RegionalAttack;
import eod.event.Event;
import eod.event.ObjectEnterEvent;
import eod.event.relay.EventReceiver;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class BloodThirstFighter extends Fighter {
    public BloodThirstFighter(Player player) {
        super(player, 4, 3, Party.RED);
        registerReceiver(new OwnedAbilities());
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
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        RegionalAttack a = RequestRegionalAttack(attack).from(this).to(player, getAttackRange(), 1);
        executor.tryToExecute(a);
        afterAttack();
        for(Damageable victim:a.getAffected()) {
            if(victim.getHp() <= 0) {
                addAttack(1);
                addHealth(1);
                attack(executor);
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
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities() {
            canHandle = new ArrayList<>();
            canHandle.add(ObjectEnterEvent.class);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof ObjectEnterEvent) {
                ObjectEnterEvent e = (ObjectEnterEvent) event;
                if(e.getObject() == BloodThirstFighter.this) {
                    BloodThirstFighter.this.attack(player);
                    teardown();
                }
            }
        }

        @Override
        public void teardown() {
            BloodThirstFighter.this.unregisterReceiver(this);
            canHandle.clear();
            canHandle = null;
        }
    }
}
