package eod.effect;

import eod.Character;
import eod.GameObject;
import eod.Player;

public class Attack implements Effect, GameObject {
    // This class should be used only in direct attacks.
    // If there's a ranged attack, create a new class.
    private int hp;
    private Player player;
    private Character from, to;
    private boolean allowConditional;
    private boolean willSuccess;
    private int range;

    public Attack(Player player, int hp) {
        this.hp = hp;
        this.player = player;
    }

    public Character attacker() {
        return from;
    }

    public Attack from(Character[] characters) {
        from = askToSelect(player, characters);
        range = from.attackRange;
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
        to = askToSelect(player.rival(), characters);
        from.attack(new Character[] {to}, hp, allowConditional, willSuccess);
        return this;
    }

    @Override
    public void teardown() {
        player = null;
    }
}
