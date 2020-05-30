package eod.card.concrete.summon.transparent;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.transparent.FacelessHuman;

import static eod.effect.EffectFunctions.Summon;

public class FacelessHumanSummon extends SummonCard {
    public FacelessHumanSummon() {
        super(4, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new FacelessHuman(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new FacelessHumanSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "無面的人";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
