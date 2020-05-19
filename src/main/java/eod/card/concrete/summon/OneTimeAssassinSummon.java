package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.transparent.OneTimeAssassin;

import static eod.effect.EffectFunctions.Summon;

public class OneTimeAssassinSummon extends SummonCard {
    // TODO: remove or change the name
    public OneTimeAssassinSummon() {
        super(SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new OneTimeAssassin(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new OneTimeAssassinSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public String getName() {
        return "召喚 單次僱用殺手";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
