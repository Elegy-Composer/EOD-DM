package eod.effect;

import eod.Character;
import eod.GameObject;
import eod.Player;

public class Attack implements Effect, GameObject {
    // This class should be used only in direct attacks.
    // If there's a ranged attack, create a new class.
    private int hp;
    private Player player;
    private Character attacker, target;
    private boolean allowConditional;
    private boolean willSuccess;
    private int range;

    public Attack(Player player, int hp) {
        this.hp = hp;
        this.player = player;
    }

    public Character attacker() {
        return attacker;
    }

    public Attack from(Character[] characters) {
        attacker = askToSelectFrom(characters);
        range = attacker.attackRange;
        return this;
    }

    public Attack allowCondition(boolean allow) {
        allowConditional = allow;
        return this;
    }

    public Attack willConditionSuccess(boolean success) {
        this.willSuccess = success;
        return this;
    }

    public Attack to(Character[] characters) {
        target = askToSelectFrom(characters);
        target.isTargeted = true;

        attacker.attack(target, hp, allowConditional, willSuccess);
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
