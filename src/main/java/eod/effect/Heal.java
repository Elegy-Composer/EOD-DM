package eod.effect;

import eod.warObject.Touchable;
import eod.warObject.WarObject;
import eod.GameObject;
import eod.Player;

public class Heal implements Effect, GameObject {
    private int hp;
    private Player player;

    public Heal(Player player, int hp) {
        this.hp = hp;
        this.player = player;
    }

    public void from(WarObject[] objects) {
        Touchable selected = (Touchable) askToSelectFrom(objects);
        selected.heal(hp);
    }

    @Override
    public void teardown() {
        player = null;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
