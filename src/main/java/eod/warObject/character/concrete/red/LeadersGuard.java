package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.LeadersGuardSummon;
import eod.effect.RegionalAttack;
import eod.exceptions.NotSupportedException;
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
    public void attack() {
        SpecialRegionalAttack SRA = (SpecialRegionalAttack) RequestRegionalAttack(attack).from(this);
        SRA.to(getAttackRange(), 1);
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBase();
    }

    private class SpecialRegionalAttack extends RegionalAttack {
        public SpecialRegionalAttack(Player player, int hp) {
            super(player, hp);
        }

        @Override
        public RegionalAttack to(ArrayList<Point> candidates, int number) {
            Point target = askToSelectOneFrom(candidates);
            if(player.getBoard().getSurrounding(player.getLeader().position, 1).contains(target)) {
                param.hp *= 2;
            }
            ArrayList<Point> singleTarget = new ArrayList<>(1);
            singleTarget.add(target);
            try {
                affected.addAll(attacker.attack(singleTarget, param));
            } catch (NotSupportedException e) {
                System.out.println(e.toString());
            }
            return this;
        }
    }
}
