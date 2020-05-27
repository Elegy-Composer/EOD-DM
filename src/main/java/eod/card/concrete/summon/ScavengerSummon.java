package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.Scavenger;

import static eod.effect.EffectFunctions.Summon;

public class ScavengerSummon extends SummonCard {
    public ScavengerSummon() {
        super(1, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new Scavenger(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new ScavengerSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 清道夫";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
