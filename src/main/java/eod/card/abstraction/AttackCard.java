package eod.card.abstraction;

import eod.Player;

public abstract class AttackCard extends ActionCard {
    public AttackCard(Player p, int lv) {
        super(p, lv);
    }

    public abstract void attack();
}
