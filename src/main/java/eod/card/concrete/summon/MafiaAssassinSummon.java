package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.red.MafiaAssassin;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class MafiaAssassinSummon extends SummonCard {
    public MafiaAssassinSummon() {
        super(3, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new MafiaAssassin(player)).from(summonRange());
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
