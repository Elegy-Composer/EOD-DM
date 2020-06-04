package eod.card.concrete.summon.red;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.ShooterSummon;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.GuerrillaShooter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class GuerrillaShooterSummon extends ShooterSummon {
    public GuerrillaShooterSummon() {
        super(2, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        ArrayList<Point> canEnter = player.getBaseEmpty();
        canEnter.addAll(player.getConflictEmpty());
        return Summon(new GuerrillaShooter(player)).onOnePointOf(player, canEnter);
    }

    @Override
    public Card copy() {
        Card c = new GuerrillaShooterSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 游擊隊槍手";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
