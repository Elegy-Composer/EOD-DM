package eod.card.abstraction.action;

import eod.Player;
import eod.card.abstraction.Card;

public abstract class ActionCard extends Card {
    public final int level;
    public ActionCard(Player p, int lv) {
        super(p);
        level = lv;
    }

    public abstract void applyEffect();
}
