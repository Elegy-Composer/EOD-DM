package eod.card.abstraction.action;

import eod.Player;
import eod.card.abstraction.ActionCard;

public abstract class ConditionalCard extends ActionCard {
    public ConditionalCard(Player p, int lv) {
        super(p, lv);
    }

    @Override
    public int getCost() {
        return 0;
    }
}
