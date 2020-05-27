package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.AssaultDirector;

import static eod.effect.EffectFunctions.Summon;

public class AssaultDirectorSummon extends SummonCard {
    public AssaultDirectorSummon() {
        super(3, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new AssaultDirector(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new AssaultTeamLeaderSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 突擊指揮";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
