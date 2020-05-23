package eod.warObject.character.concrete.red;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.FrenzyCommanderSummon;
import eod.effect.Summon;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

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
    public void attack() {
        super.attack();
        Summon s = Summon(player, new Gangster(player));
        s.from(getAttackRange());
        Gangster g = (Gangster) s.getObject();
        g.addHealth(2);
        g.addAttack(2);

        afterAttack();
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.emptySpace = true;
        return player.getBoard().allSpaces(new Point(Gameboard.firstLine, 0), param);
    }
}
