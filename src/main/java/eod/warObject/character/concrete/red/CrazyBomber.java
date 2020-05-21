package eod.warObject.character.concrete.red;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.CrazyBomberSummon;
import eod.effect.RegionalAttack;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class CrazyBomber extends Character {
    public CrazyBomber(Player player) {
        super(player, 1, 2, Party.RED);
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
    public void attack() {
        super.attack();
        Point p = player.selectPosition(getAttackRange());

        ArrayList<Point> singleTarget = new ArrayList<>();
        singleTarget.add(p);
        RequestRegionalAttack(player, 1).from(this).to(singleTarget);

        PointParam param = new PointParam();
        param.range = 1;
        SpecialRegionalAttack SRA = (SpecialRegionalAttack) RequestRegionalAttack(player, 1);
        SRA.to(player.getBoard().getSurrounding(p, param));
    }

    @Override
    public void die() {
        PointParam param = new PointParam();
        param.range = 1;
        SpecialRegionalAttack SRA = (SpecialRegionalAttack) RequestRegionalAttack(player, 3).from(this);
        SRA.to(player.getBoard().getSurrounding(position, param));
        player.loseObject(this);
        teardown();
    }

    public class SpecialRegionalAttack extends RegionalAttack {

        public SpecialRegionalAttack(Player player, int hp) {
            super(player, hp);
        }

        @Override
        public RegionalAttack to(ArrayList<Point> targets) {
            Gameboard board = player.getBoard();
            for(Point p:targets) {
                try {
                    Damageable target = board.getObjectOn(p.x, p.y);
                    target.damage(param.hp);
                } catch (IllegalArgumentException e) {
                    System.out.println("There's no object on the point, skipping.");
                }
            }
            return this;
        }
    }
}
