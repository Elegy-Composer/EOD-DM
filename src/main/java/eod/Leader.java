package eod;

import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;

import java.util.ArrayList;

public abstract class Leader implements WarObject, GameObject {
    private int max_hp, hp;
    private Player player;
    private ArrayList<NormalCard> specialOrder;

    public Leader(Player player, int hp) {
        this.player = player;
        max_hp = hp;
        this.hp = max_hp;
        specialOrder = generateSpecialOrder();
    }

    abstract ArrayList<NormalCard> generateSpecialOrder();

    public void damage(int hp) {
        this.hp -= hp;
        if(this.hp < 0) {
            teardown();
            player.loseLeader();
        }
    }

    public Card getSpecialOrder() {
        return specialOrder.remove(0);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public void teardown() {
        player = null;
    }
}
