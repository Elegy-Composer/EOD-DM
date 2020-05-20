package eod.card.abstraction.action;

import eod.Player;

public abstract class AttackCard extends ActionCard {
    public Player rival;

    public abstract void attack();

    @Override
    public void applyEffect() {
        rival = player.rival();
        attack();
    }
}
