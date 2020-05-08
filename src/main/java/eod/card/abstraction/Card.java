package eod.card.abstraction;

import eod.GameObject;
import eod.Party;
import eod.Player;

public abstract class Card implements GameObject {
    
    public abstract Card copy();
    public abstract int getCost();
    public abstract String getName();
    public abstract Party getParty();

    protected Player player;
    public Card(Player p) {
        player = p;
    }

    public boolean cardTypeEquals(Class<? extends Card> c) {
        return c.isInstance(this);
    }

    public abstract void effect();

    @Override
    public void teardown() {
        this.player = null;
    }
}
