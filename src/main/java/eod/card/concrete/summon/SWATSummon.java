package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.blue.SWAT;

import static eod.effect.EffectFunctions.Summon;

public class SWATSummon extends SummonCard {
    public SWATSummon() {
        super(2, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new SWAT(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new SWATSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 特勤警員";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
