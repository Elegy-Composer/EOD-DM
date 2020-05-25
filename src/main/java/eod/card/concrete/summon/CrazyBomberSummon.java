package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.CrazyBomber;

import static eod.effect.EffectFunctions.Summon;

public class CrazyBomberSummon extends SummonCard {
    public CrazyBomberSummon() {
        super(3, SummonCardType.NORMAL);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new CrazyBomber(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new CrazyBomberSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 瘋狂炸彈客";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
