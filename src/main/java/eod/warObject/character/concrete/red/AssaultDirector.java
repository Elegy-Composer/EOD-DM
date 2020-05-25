package eod.warObject.character.concrete.red;

import eod.BoardPosition;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.AssaultDirectorSummon;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class AssaultDirector extends Character {
    public AssaultDirector(Player player) {
        super(player, 2, 1, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new AssaultDirectorSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "突擊指揮";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);

        Gameboard board = player.getBoard();
        PointParam param = new PointParam();
        param.range = 1;

        Gangster gangster = (Gangster) player.selectObject(WarObject(board).which(OwnedBy(player)).which(Being(Gangster.class)).get());
        do {
            ArrayList<Point> front = player.getFront(gangster.position, param);
            if(front.size() == 0) {
                break;
            }
            Point p = front.get(0);
            if(board.hasObjectOn(p.x, p.y)) {
                gangster.addAttack(1);
                gangster.addHealth(1);
                if(player.getPosition(position) == BoardPosition.ENEMY_BASE) {
                    gangster.addAttack(2);
                    gangster.addHealth(2);
                }
                gangster.attack(executor);
                break;
            } else {
                gangster.moveTo(p);
            }
        } while (true);

        afterAttack();
    }
}
