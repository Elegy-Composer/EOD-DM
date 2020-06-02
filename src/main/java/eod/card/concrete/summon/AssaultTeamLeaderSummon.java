package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.AssaultTeamLeader;

import static eod.effect.EffectFunctions.Summon;

public class AssaultTeamLeaderSummon extends SummonCard {
    public AssaultTeamLeaderSummon() {
        super(1, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new AssaultTeamLeader(player)).onOnePointOf(player, player.getBase());
    }

    @Override
    public Card copy() {
        Card c = new AssaultTeamLeaderSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 特攻隊長";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
