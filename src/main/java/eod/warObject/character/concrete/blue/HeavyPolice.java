package eod.warObject.character.concrete.blue;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.blue.HeavyPoliceSummon;
import eod.event.Event;
import eod.event.BeforeObjectDamageEvent;
import eod.event.relay.EventReceiver;
import eod.param.DamageParam;
import eod.param.PointParam;
import eod.effect.EffectExecutor;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class HeavyPolice extends Character {
    public HeavyPolice(Player player) {
        super(player, 5,5, Party.BLUE);
        registerReceiver(new DamageLessThan2());
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard card = new HeavyPoliceSummon();
        card.setPlayer(player);
        return card;
    }

    @Override
    public String getName() {
        return "重裝備武警";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        executor.tryToExecute(
                RequestRegionalAttack(attack).from(this).to(getAttackRange())
        );

        afterAttack();
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().get4Ways(position, param);
    }

    public class DamageLessThan2 implements EventReceiver {
        private ArrayList<Class<? extends Event>> canHandle;

        public DamageLessThan2() {
            this.canHandle = new ArrayList<>();
            canHandle.add(BeforeObjectDamageEvent.class);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof BeforeObjectDamageEvent) {
                BeforeObjectDamageEvent e = (BeforeObjectDamageEvent) event;
                DamageParam param = e.getParam();
                if(e.getVictim() == HeavyPolice.this && !param.realDamage) {
                    if(param.damage > 2) {
                        param.damage = 2;
                    }
                }
            }
        }

        @Override
        public void teardown() {
            canHandle.clear();
            HeavyPolice.this.unregisterReceiver(this);
        }
    }
}
