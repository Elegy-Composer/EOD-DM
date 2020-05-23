package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.transparent.OwnerlessAssassin;

import static eod.effect.EffectFunctions.Summon;

public class OwnerlessAssassinSummon extends SummonCard {
    public OwnerlessAssassinSummon() {
        super(2, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new OwnerlessAssassin(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new OwnerlessAssassinSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 無主的殺手";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
