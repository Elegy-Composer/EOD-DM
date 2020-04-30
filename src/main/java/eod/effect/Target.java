package eod.effect;

import eod.Character;
import eod.GameObject;

public class Target implements Effect, GameObject {
    private Character[] targets;

    public Target on(Character[] targets) {
        this.targets = targets;
        for(Character target:targets) {
            target.isTargeted = true;
        }
        return this;
    }

    public void unTarget() {
        for(Character target:targets) {
            target.isTargeted = false;
        }
    }

    @Override
    public void teardown() {
        targets = null;
    }
}
