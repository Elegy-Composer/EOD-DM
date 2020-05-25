package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.LeadersGuardSummon;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.effect.RegionalAttack;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.character.abstraction.assaulter.Shooter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class LeadersGuard extends Shooter {
    public LeadersGuard(Player player) {
        super(player, 5, 2, Party.RED);
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
        SpecialRegionalAttack SRA = (SpecialRegionalAttack) RequestRegionalAttack(attack).from(this);
        SRA.to(player, getAttackRange(), 1);
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBase();
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
