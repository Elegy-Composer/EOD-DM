package eod.card.abstraction;

import eod.Player;

public abstract class ActionCard extends Card {
    public final int level;
    public ActionCard(Player p, int lv) {
        super(p);
        level = lv;
    }

    public abstract void effect();
}
