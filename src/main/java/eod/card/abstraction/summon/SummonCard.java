package eod.card.abstraction.summon;

import eod.Player;
import eod.card.abstraction.Card;

public abstract class SummonCard extends Card {
    public SummonCard(Player p) {
        super(p);
    }

    @Override
    public void effect() {
        summon();
    }

    public abstract void summon();
}
