package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.transparent.AssassinsIntermediary;

import static eod.effect.EffectFunctions.Summon;

public class AssassinsIntermediarySummon extends SummonCard {
    public AssassinsIntermediarySummon() {
        super(2, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new AssassinsIntermediary(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new AssassinsIntermediarySummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 殺手掮客";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
