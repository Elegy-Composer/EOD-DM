package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.red.Gangster;

import static eod.effect.EffectFunctions.Summon;

public class GangsterSummon extends SummonCard {
    public GangsterSummon(Player p) {
        super(p, SummonCardType.TOKEN);
    }

    @Override
    public void summon() {
        Summon(player, new Gangster(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new GangsterSummon(player);
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "召喚 好戰分子";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
