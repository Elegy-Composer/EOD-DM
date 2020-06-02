package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.LeadersGuardSummon;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.effect.RegionalAttack;
import eod.event.Event;
import eod.event.ObjectMovingEvent;
import eod.event.relay.EventReceiver;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.character.abstraction.assaulter.Shooter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class LeadersGuard extends Shooter {
    public LeadersGuard(Player player) {
        super(player, 5, 2, Party.RED);
        registerReceiver(new OwnedAbilities());
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new LeadersGuardSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "首領貼身保鏢";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        SpecialRegionalAttack SRA = new SpecialRegionalAttack(attack);
        executor.tryToExecute(
            SRA.from(this).to(player, getAttackRange(), 1)
        );
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBase();
    }

    private class OwnedAbilities implements EventReceiver {
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities() {
            canHandle = new ArrayList<>();
            canHandle.add(ObjectMovingEvent.class);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof ObjectMovingEvent) {
                ObjectMovingEvent e = (ObjectMovingEvent) event;
                if(e.getObject() == LeadersGuard.this && !player.inBase(e.getNewPos())) {
                    e.setNewPos(e.getOrigPos());
                }
            }
        }

        @Override
        public void teardown() {
            LeadersGuard.this.unregisterReceiver(this);
            canHandle.clear();
            canHandle = null;
        }
    }

    private class SpecialRegionalAttack extends RegionalAttack {
        public SpecialRegionalAttack(int hp) {
            super(hp);
        }

        @Override
        public RegionalAttack to(Player player, ArrayList<Point> candidates, int number) {
            Point target = askToSelectOneFrom(player, candidates);
            PointParam pointParam = new PointParam();
            pointParam.range = 1;
            if(player.getBoard().getSurrounding(player.getLeader().position, pointParam).contains(target)) {
                param.hp *= 2;
            }
            ArrayList<Point> singleTarget = new ArrayList<>(1);
            singleTarget.add(target);
            return to(singleTarget);
        }
    }
}
