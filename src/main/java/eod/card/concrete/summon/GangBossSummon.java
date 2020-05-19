package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.red.GangBoss;

import static eod.effect.EffectFunctions.Summon;

public class GangBossSummon extends SummonCard {
    public GangBossSummon(Player p) {
        super(p, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new GangBoss(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new GangBossSummon(player);
    }

    @Override
    public int getCost() {
        return 5;
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
