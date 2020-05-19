package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.blue.HeavyPolice;

import static eod.effect.EffectFunctions.Summon;

public class HeavyPoliceSummon extends SummonCard {
    public HeavyPoliceSummon() {
        super(SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new HeavyPolice(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new HeavyPoliceSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public int getCost() {
        return 6;
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
