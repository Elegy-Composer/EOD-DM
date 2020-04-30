package eod.card.abstraction.action;

import eod.Player;
import eod.card.abstraction.ActionCard;

public abstract class NormalCard extends ActionCard {
    public NormalCard(Player p, int lv) {
        super(p, lv);
    }

}
