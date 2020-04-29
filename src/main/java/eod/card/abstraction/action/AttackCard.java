package eod.card.abstraction.action;

import eod.Player;
import eod.card.abstraction.ActionCard;

public abstract class AttackCard extends ActionCard {
    public AttackCard(Player p, int lv) {
        super(p, lv);
    }

    public abstract void attack();
}
