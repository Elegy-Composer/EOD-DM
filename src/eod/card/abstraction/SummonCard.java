package eod.card.abstraction;

import eod.Player;

public abstract class SummonCard extends Card{
    public SummonCard(Player p) {
        super(p);
    }

    public abstract void summon();
}
