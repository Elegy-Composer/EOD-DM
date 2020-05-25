package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.ToughGuy;

import static eod.effect.EffectFunctions.Summon;

public class ToughGuySummon extends SummonCard {
    public ToughGuySummon() {
        super(6, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new ToughGuy(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new ToughGuySummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 蠻勇的巨漢";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
