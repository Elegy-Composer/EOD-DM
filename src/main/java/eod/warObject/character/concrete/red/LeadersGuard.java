package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.LeadersGuardSummon;
import eod.warObject.character.abstraction.assaulter.Shooter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class LeadersGuard extends Shooter {
    public LeadersGuard(Player player) {
        super(player, 5, 2, -1, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        return new LeadersGuardSummon(player);
    }

    @Override
    public String getName() {
        return "首領貼身保鏢";
    }

    @Override
    public void attack() {
        RequestRegionalAttack(player, decideAttack()).from(this).to(getAttackRange(), 1);
    }

    private int decideAttack() {
        if(player.getBoard().get4Ways(position, 1).contains(player.getLeader().position)) {
            return 2*attack;
        } else {
            return attack;
        }
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBase();
    }
}
