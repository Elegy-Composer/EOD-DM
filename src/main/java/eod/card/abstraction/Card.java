package eod.card.abstraction;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.effect.EffectExecutor;

public abstract class Card implements GameObject {
    private int cost;
    public Card(int cost) {
        this.cost = cost;
    }
    
    public abstract Card copy();
    public abstract String getName();
    public abstract Party getParty();

    public int getCost() {
        return cost;
    }

    public void setCost(int n) {
        cost = n;
    }

    public void cutCost(int c) {
        if(cost - c <= 0) {
            cost = 0;
        } else {
            cost -= c;
        }
    }

    protected Player player;
    public void setPlayer(Player p) {
        player = p;
    }



    public boolean cardTypeEquals(Class<? extends Card> c) {
        return c.isInstance(this);
    }
    public String getStringDescription() {
        return getName() + "(" + getParty().toString() + ")";
    }

    public abstract void effect(EffectExecutor executor);

    @Override
    public void teardown() {
        this.player = null;
    }
}
