package eod.card.concrete.summon.red;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.FighterSummon;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.GangBoss;

import static eod.effect.EffectFunctions.Summon;

public class GangBossSummon extends FighterSummon {
    public GangBossSummon() {
        super(5, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new GangBoss(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new GangBossSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "武鬥派頭頭";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
