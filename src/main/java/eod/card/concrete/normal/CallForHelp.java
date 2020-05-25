package eod.card.concrete.normal;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.EffectExecutor;
import eod.warObject.character.concrete.red.Gangster;

import static eod.effect.EffectFunctions.Summon;

public class CallForHelp extends NormalCard {
    public CallForHelp() {
        super(2);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        for(int i = 0;i < 2;i++) {
            executor.tryToExecute(
                Summon(new Gangster(player)).onOnePointOf(player, player.getConflictEmpty())
            );
        }
    }

    @Override
    public Card copy() {
        Card c = new CallForHelp();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "呼叫幫手";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
