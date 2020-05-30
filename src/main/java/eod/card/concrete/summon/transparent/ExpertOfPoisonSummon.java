package eod.card.concrete.summon.transparent;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.transparent.ExpertOfPoison;

import static eod.effect.EffectFunctions.Summon;

public class ExpertOfPoisonSummon extends SummonCard {
    public ExpertOfPoisonSummon() {
        super(5, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new ExpertOfPoison(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new ExpertOfPoisonSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 毒殺專家";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
