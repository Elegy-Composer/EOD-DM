package eod.card.abstraction.action;

import eod.Player;
import eod.card.concrete.conditional.ConditionType;

public abstract class ConditionalCard extends ActionCard {
    public ConditionalCard(Player p, int lv) {
        super(p, lv);
    }
    public abstract ConditionType[] capableConditions();

    @Override
    public int getCost() {
        return 0;
    }
}
