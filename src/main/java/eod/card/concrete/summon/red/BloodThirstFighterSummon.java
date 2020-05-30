package eod.card.concrete.summon.red;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.FighterSummon;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.BloodThirstFighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class BloodThirstFighterSummon extends FighterSummon {
    public BloodThirstFighterSummon() {
        super(5, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new BloodThirstFighter(player)).onOnePointOf(player, baseAndConflict());
    }

    @Override
    public Card copy() {
        Card c = new BloodThirstFighterSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 嗜血的戰鬥狂";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }

    private ArrayList<Point> baseAndConflict() {
        ArrayList<Point> points = player.getBaseEmpty();
        points.addAll(player.getConflictEmpty());
        return points;
    }
}
