package eod.card.abstraction;

import eod.Player;

public abstract class Card implements ICard{
    public abstract Card copy();

    protected Player player;
    public Card(Player p) {
        player = p;
    }

}
