package eod.card.abstraction.summon;

import eod.card.abstraction.Card;
import eod.effect.EffectExecutor;
import eod.effect.Summon;

public abstract class SummonCard extends Card {
    public final SummonCardType type;
    public SummonCard(int cost, SummonCardType type) {
        super(cost);
        this.type = type;
    }

    @Override
    public void effect(EffectExecutor executor) {
        executor.tryToExecute(summonEffect());
    }

    public abstract Summon summonEffect();
}
