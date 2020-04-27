package eod.card.abstraction;

import eod.GameObject;
import eod.Player;

public abstract class Card implements ICard, GameObject {
    public abstract Card copy();

    protected Player player;
    public Card(Player p) {
        player = p;
    }

    @Override
    public void teardown() {
        this.player = null;
    }
}
