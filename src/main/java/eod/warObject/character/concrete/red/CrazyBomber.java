package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.CrazyBomberSummon;
import eod.effect.EffectExecutor;
import eod.effect.RegionalAttack;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.relay.EventReceiver;
import eod.exceptions.NotSupportedException;
import eod.param.DamageParam;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class CrazyBomber extends Character {
    public CrazyBomber(Player player) {
        super(player, 1, 2, Party.RED);
        new OwnedAbilities(this);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new CrazyBomberSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "瘋狂炸彈客";
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBoard().allSpaces(new Point(Gameboard.firstLine, 0), new PointParam());
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        Point p = player.selectPosition(getAttackRange());

        ArrayList<Point> singleTarget = new ArrayList<>();
        singleTarget.add(p);
        RequestRegionalAttack(attack).from(this).to(singleTarget);

        PointParam param = new PointParam();
        param.range = 1;
        SpecialRegionalAttack SRA = new SpecialRegionalAttack(1);
        SRA.to(player.getBoard().getSurrounding(p, param));

        afterAttack();
    }

    private class OwnedAbilities implements EventReceiver {
        private CrazyBomber holder;
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities(CrazyBomber holder) {
            holder.registerReceiver(this);
            this.holder = holder;
            canHandle = new ArrayList<>();
            canHandle.add(ObjectDeadEvent.class);
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
                    PointParam param = new PointParam();
                    param.range = 1;
                    SpecialRegionalAttack SRA = new SpecialRegionalAttack(3);
                    SRA.from(holder).to(player.getBoard().getSurrounding(position, param));
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

    private class SpecialRegionalAttack extends RegionalAttack {

        public SpecialRegionalAttack(int hp) {
            super(hp);
        }

        @Override
        public RegionalAttack to(ArrayList<Point> targets) {
            Gameboard board = player.getBoard();
            for(Point p:targets) {
                try {
                    Damageable target = board.getObjectOn(p.x, p.y);
                    target.damage(new DamageParam(param.hp));
                } catch (IllegalArgumentException e) {
                    System.out.println("There's no object on the point, skipping.");
                }
            }
            return this;
        }
    }
}
