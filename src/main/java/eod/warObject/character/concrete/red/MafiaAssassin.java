package eod.warObject.character.concrete.red;

import eod.Game;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.red.MafiaAssassinSummon;
import eod.effect.EffectExecutor;
import eod.effect.RegionalAttack;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.assaulter.Assassin;
import eod.warObject.leader.Leader;

import java.awt.*;
import java.util.ArrayList;

public class MafiaAssassin extends Assassin {
    public MafiaAssassin(Player player) {
        super(player, 1, 3, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new MafiaAssassinSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "組織暗殺者";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        SpecialRegionalAttack a = new SpecialRegionalAttack(attack);
        a.from(this).to(player, getAttackRange(), 1);
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().get4Ways(position, param);
    }

    public class SpecialRegionalAttack extends RegionalAttack {
        public SpecialRegionalAttack(int hp) {
            super(hp);
        }

        @Override
        public void action(EffectExecutor executor) throws WrongExecutorException {
            Game game = castExecutor(executor);
            Gameboard board = game.getBoard();

            for(Point p:getTargets()) {
                try {
                    WarObject target = board.getObjectOn(p.x, p.y);
                    if(target instanceof Leader) {
                        param.hp *= 2;
                    }
                    if(hasStatus(Status.FURIOUS)) {
                        param.hp *= 2;
                    }
                    affected.addAll(game.damage(attacker, p, param));
                } catch (IllegalArgumentException e) {
                    System.out.println("There's no object on ("+p.x+", "+p.y+"). \nSkipping.");
                } catch (NotSupportedException e) {
                    System.out.println("The attacker "+((WarObject)attacker).getName()+" cannot attack a target directly.");
                }
            }
        }
    }
}
