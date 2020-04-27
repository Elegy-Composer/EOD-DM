package eod.effect;

import eod.GameObject;
import eod.WarObject;

public class Heal implements Effect, GameObject {
    private int hp;

    public Heal(int hp) {
        this.hp = hp;
    }

    public void on(WarObject[] targets) {

    }

    @Override
    public void teardown() {
        //don't need to do anything
    }
}
