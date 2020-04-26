package eod.card.abstraction;

import eod.Player;

public abstract class NormalCard extends ActionCard {
    public NormalCard(Player p) {
        super(p);
    }

    public abstract void effect();
}
