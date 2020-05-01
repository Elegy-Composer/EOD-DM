package eod.card.abstraction.action;

import eod.Player;

public abstract class AttackCard extends ActionCard {
    public Player rival;
    public AttackCard(Player p, int lv) {
        super(p, lv);
        rival = p.rival();
    }

    public abstract void attack();

    @Override
    public void effect() {
        attack();
    }
}
