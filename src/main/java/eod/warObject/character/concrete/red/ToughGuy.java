package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.ToughGuySummon;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class ToughGuy extends Fighter {
    public ToughGuy(Player player) {
        super(player, 6, 3, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new ToughGuySummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "蠻勇的巨漢";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        RequestRegionalAttack(attack).from(this).to(getAttackRange());
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }

    @Override
    public void realDamage(int val) {
        super.realDamage(val);
        addAttack(2);
        addHealth(2);
    }
}
