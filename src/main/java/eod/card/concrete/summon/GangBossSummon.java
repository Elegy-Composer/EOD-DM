package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.red.GangBoss;

import static eod.effect.EffectFunctions.Summon;

public class GangBossSummon extends SummonCard {
    public GangBossSummon() {
        super(5, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new GangBoss(player)).from(player.getBaseEmpty());
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
