package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

public class Heal implements Effect, GameObject {
    private int hp;
    private Player player;

    public Heal(Player player, int hp) {
        this.hp = hp;
        this.player = player;
    }

    public void from(WarObject[] objects) {
        Damageable selected = (Damageable) askToSelectFrom(objects);
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
