package eod.card.abstraction.action;

import eod.Player;

public abstract class AttackCard extends ActionCard {
    public Player rival;

    @Override
    public void setPlayer(Player p) {
        super.setPlayer(p);
        rival = p.rival();
    }

    public abstract void attack();

    @Override
    public void applyEffect() {
        attack();
    }
}
