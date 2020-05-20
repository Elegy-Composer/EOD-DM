package eod.card.abstraction.action;

import eod.Player;

public abstract class AttackCard extends ActionCard {
    public Player rival;

    public abstract void attack();

    @Override
    public void setPlayer(Player p) {
        super.setPlayer(p);
        rival = p.rival();
    }

    @Override
    public void applyEffect() {
        attack();
    }
}
