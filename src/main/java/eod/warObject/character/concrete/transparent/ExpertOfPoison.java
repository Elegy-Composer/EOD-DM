package eod.warObject.character.concrete.transparent;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.ExpertOfPoisonSummon;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.GiveStatus;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.InRangeOf;

public class ExpertOfPoison extends Character {
    public ExpertOfPoison(Player player) {
        super(player, 2, 2, Party.TRANSPARENT);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new ExpertOfPoisonSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "毒殺專家";
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBoard().allSpaces(new Point(Gameboard.firstLine, 0), new PointParam());
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        try {
            Point p = player.selectPosition(getAttackRange());
            WarObject object = player.getBoard().getObjectOn(p.x, p.y);
            player.tryToExecute(
                GiveStatus(Status.POISON, Effect.HandlerType.Rival).to(object)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Some idiot selected an empty space. Skipping");
        }

        afterAttack();
    }
}
