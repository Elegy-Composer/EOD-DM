package eod.warObject.character.concrete.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.transparent.FacelessHumanSummon;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static eod.specifier.WarObjectSpecifier.WarObject;

public class FacelessHuman extends Character {
    public FacelessHuman(Player player) {
        super(player, 3, 3, Party.TRANSPARENT);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new FacelessHumanSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBoard().allSpaces(new Point(-1, 0), new PointParam());
    }

    @Override
    public String getName() {
        return "召喚 無面的人";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        try {
            player.transferObjectTo(this, player.selectObject(
                    WarObject(player.getBoard()).get()
            ).getClass().getConstructor(Player.class).newInstance(player));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
    }
}
