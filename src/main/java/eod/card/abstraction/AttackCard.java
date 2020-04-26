package eod.card.abstraction;

import eod.Player;

public abstract class AttackCard extends ActionCard {
    public AttackCard(Player p) {
        super(p);
    }

    public abstract void attack();
}
