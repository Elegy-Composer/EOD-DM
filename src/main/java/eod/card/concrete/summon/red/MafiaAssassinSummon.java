package eod.card.concrete.summon.red;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.AssassinSummon;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.MafiaAssassin;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class MafiaAssassinSummon extends AssassinSummon {
    public MafiaAssassinSummon() {
        super(3, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new MafiaAssassin(player)).onOnePointOf(player, summonRange());
    }

    private ArrayList<Point> summonRange() {
        ArrayList<Point> r = player.getBaseEmpty();
        r.addAll(player.rival().getBaseEmpty());
        return r;
    }

    @Override
    public Card copy() {
        Card c = new MafiaAssassinSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 組織暗殺者";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
