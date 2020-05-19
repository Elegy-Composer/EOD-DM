package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.blue.SecureGuardBot;

import static eod.effect.EffectFunctions.Summon;

public class SecureGuardBotSummon extends SummonCard {
    public SecureGuardBotSummon() {
        super(3, SummonCardType.TOKEN);
    }

    @Override
    public void summon() {
        Summon(player, new SecureGuardBot(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new SecureGuardBotSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 維安警備機械";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
