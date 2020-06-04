package eod.card.concrete.summon.transparent;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.transparent.IntelligenceVendor;

import static eod.effect.EffectFunctions.Summon;

public class IntelligenceVendorSummon extends SummonCard {
    public IntelligenceVendorSummon() {
        super(3, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new IntelligenceVendor(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new IntelligenceVendorSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 情報販";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
