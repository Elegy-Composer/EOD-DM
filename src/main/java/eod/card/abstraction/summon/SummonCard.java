package eod.card.abstraction.summon;

import eod.Player;
import eod.card.abstraction.Card;

public abstract class SummonCard extends Card {
    public final SummonCardType type;
    public SummonCard(int cost, SummonCardType type) {
        super(cost);
        this.type = type;
    }

    @Override
    public void effect() {
        summon();
    }

    public abstract void summon();
}
