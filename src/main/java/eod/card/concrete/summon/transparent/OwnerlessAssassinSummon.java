package eod.card.concrete.summon.transparent;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.AssassinSummon;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.transparent.OwnerlessAssassin;

import static eod.effect.EffectFunctions.Summon;

public class OwnerlessAssassinSummon extends AssassinSummon {
    public OwnerlessAssassinSummon() {
        super(2, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new OwnerlessAssassin(player)).onOnePointOf(player, player.getBaseEmpty());
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
