package eod.card.concrete.summon.blue;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.blue.HeavyPolice;

import static eod.effect.EffectFunctions.Summon;

public class HeavyPoliceSummon extends SummonCard {
    public HeavyPoliceSummon() {
        super(6, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new HeavyPolice(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new HeavyPoliceSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 重裝備武警";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
