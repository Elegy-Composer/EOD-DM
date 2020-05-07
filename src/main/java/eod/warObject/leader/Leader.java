package eod.warObject.leader;

import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class Leader extends WarObject {
    private int max_hp, hp;
    private ArrayList<NormalCard> specialOrder;

    public Leader(Player player, int hp) {
        super(player, new Point(-1, -1));
        max_hp = hp;
        this.hp = max_hp;
        specialOrder = generateSpecialOrder();
    }

    public Leader enterArena() {
        ArrayList<Point> points = player.getBase();
        position = player.selectPosition(points);
        player.summonObject(this, position.x, position.y);
        return this;
    }

    protected abstract ArrayList<NormalCard> generateSpecialOrder();

    public abstract void attack();

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
