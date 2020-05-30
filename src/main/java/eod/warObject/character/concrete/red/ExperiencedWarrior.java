package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.red.ExperiencedWarriorSummon;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class ExperiencedWarrior extends Fighter {
    public ExperiencedWarrior(Player player) {
        super(player, 5, 5, Party.RED);
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        executor.tryToExecute(
            RequestRegionalAttack(attack).from(this).to(player, getAttackRange(), 1)
        );

        afterAttack();
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new ExperiencedWarriorSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "身經百戰的戰士";
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }
}
