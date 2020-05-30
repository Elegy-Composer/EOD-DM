package eod.warObject.character.concrete.red;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.red.FrenzyCommanderSummon;
import eod.effect.EffectExecutor;
import eod.effect.Summon;
import eod.param.PointParam;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.*;

public class FrenzyCommander extends Character {
    public FrenzyCommander(Player player) {
        super(player, 3, 3, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new FrenzyCommanderSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "暴亂司令";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        Summon s = Summon(new Gangster(player));
        executor.tryToExecute(
            s.onOnePointOf(player, getAttackRange())
        );
        Gangster g = (Gangster) s.getObject();
        executor.tryToExecute(
            IncreaseAttack(2).to(g)
        );
        executor.tryToExecute(
            IncreaseHealth(2).to(g)
        );

        afterAttack();
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.emptySpace = true;
        return player.getBoard().allSpaces(new Point(Gameboard.firstLine, 0), param);
    }
}
