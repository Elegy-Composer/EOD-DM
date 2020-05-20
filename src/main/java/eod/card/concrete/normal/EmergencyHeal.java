package eod.card.concrete.normal;

import eod.Party;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;

import static eod.specifier.condition.Conditions.Injured;
import static eod.specifier.timing.Timings.LastEnemyTurn;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.effect.EffectFunctions.*;

public class EmergencyHeal extends NormalCard {

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "緊急治療";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        Effect effect =
                RequestHeal(3)
                    .to(player, Character(player.getBoard()).which(Injured()).in(LastEnemyTurn()).get());
        executor.tryToExecute(effect);
    }

    @Override
    public EmergencyHeal copy() {
        EmergencyHeal card = new EmergencyHeal();
        card.setPlayer(player);
        return card;
    }
}
