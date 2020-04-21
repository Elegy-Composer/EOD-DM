package eod.card.abstraction;

import eod.Player;

public abstract class ConditionalCard extends ActionCard {
    public ConditionalCard(Player p) {
        super(p);
    }

    @Override
    public int getCost() {
        return 0;
    }
}
