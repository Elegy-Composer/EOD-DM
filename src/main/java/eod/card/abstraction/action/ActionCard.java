package eod.card.abstraction.action;

import eod.Player;
import eod.card.abstraction.Card;

public abstract class ActionCard extends Card {

    public ActionCard(int cost) {
        super(cost);
    }

    @Override
    public void effect() {
        applyEffect();
    }

    public abstract void applyEffect();
}
