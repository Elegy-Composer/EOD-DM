package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.blue.SecureGuardBot;

import static eod.effect.EffectFunctions.Summon;

public class SecureGuardBotSummon extends SummonCard {
    public SecureGuardBotSummon(Player p) {
        super(p, SummonCardType.TOKEN);
    }

    @Override
    public void summon() {
        Summon(player, new SecureGuardBot(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new SecureGuardBotSummon(player);
    }

    @Override
    public int getCost() {
        return 3;
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
