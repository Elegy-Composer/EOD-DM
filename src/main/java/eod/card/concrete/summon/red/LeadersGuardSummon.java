package eod.card.concrete.summon.red;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.ShooterSummon;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.LeadersGuard;

import static eod.effect.EffectFunctions.Summon;

public class LeadersGuardSummon extends ShooterSummon {
    public LeadersGuardSummon() {
        super(3, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new LeadersGuard(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new LeadersGuardSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 首領貼身保鏢";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
