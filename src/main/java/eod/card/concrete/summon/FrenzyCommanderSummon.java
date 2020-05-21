package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.red.FrenzyCommander;

import static eod.effect.EffectFunctions.Summon;

public class FrenzyCommanderSummon extends SummonCard {
    public FrenzyCommanderSummon() {
        super(6, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new FrenzyCommander(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new FrenzyCommanderSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 暴亂司令";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
