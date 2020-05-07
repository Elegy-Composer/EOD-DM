package eod.effect;

import eod.warObject.CanAttack;
import eod.warObject.Touchable;
import eod.warObject.WarObject;
import eod.warObject.character.Character;
import eod.GameObject;
import eod.Player;

public class DirectAttack implements Effect, GameObject {
    // This class should be used only in direct attacks.
    // If there's a ranged attack, use RegionalAttack.
    private int hp;
    private Player player;
    private CanAttack attacker;
    private Touchable target;

    public DirectAttack(Player player, int hp) {
        this.hp = hp;
        this.player = player;
    }

    public CanAttack attacker() {
        return attacker;
    }

    public DirectAttack from(WarObject[] objects) {
        attacker = (CanAttack) askToSelectFrom(objects);
        return this;
    }

    public DirectAttack from(WarObject object) {
        attacker = (CanAttack) object;
        return this;
    }

    public DirectAttack to(WarObject[] objects) {
        target = (Touchable) askToSelectFrom(objects);

        attacker.attack(target, hp);
        return this;
    }

    public DirectAttack toAll(Touchable[] targets) {
        for(Touchable target:targets) {
            attacker.attack(target, hp);
        }
        return this;
    }

    @Override
    public void teardown() {
        player = null;
        attacker = null;
        target = null;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
