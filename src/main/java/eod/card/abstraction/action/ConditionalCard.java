package eod.card.abstraction.action;

import eod.Player;
import eod.card.concrete.conditional.ConditionType;

import java.util.ArrayList;

public abstract class ConditionalCard extends ActionCard {
    public ConditionalCard(Player p, int lv) {
        super(p, lv);
    }
    public abstract ArrayList<ConditionType> capableConditions();

    @Override
    public int getCost() {
        return 0;
    }

    public boolean canHandle(ConditionType type) {
        return capableConditions().contains(type);
    }
}
