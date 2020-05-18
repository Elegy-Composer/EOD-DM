package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.red.LeadersGuard;

import static eod.effect.EffectFunctions.Summon;

public class LeadersGuardSummon extends SummonCard {
    public LeadersGuardSummon() {
        super(SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new LeadersGuard(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new LeadersGuardSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public int getCost() {
        return 3;
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
