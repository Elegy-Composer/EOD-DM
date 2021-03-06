package eod.card.concrete.summon.red;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.GhostOfHatred;

import static eod.effect.EffectFunctions.Summon;

public class GhostOfHatredSummon extends SummonCard {

    public GhostOfHatredSummon() {
        super(0, SummonCardType.TOKEN);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new GhostOfHatred(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new GhostOfHatredSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 怨念的亡靈";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
