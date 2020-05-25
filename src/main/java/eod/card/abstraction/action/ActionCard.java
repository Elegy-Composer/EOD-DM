package eod.card.abstraction.action;

import eod.card.abstraction.Card;
import eod.effect.EffectExecutor;

public abstract class ActionCard extends Card {

    public ActionCard(int cost) {
        super(cost);
    }

    public abstract void applyEffect(EffectExecutor executor);

    @Override
    public void effect(EffectExecutor executor) {
        applyEffect(executor);
    }
}
