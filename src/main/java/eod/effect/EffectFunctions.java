package eod.effect;

import eod.Player;

public class EffectFunctions {
    public static Heal RequestHeal(Player player, int hp) {
        return new Heal(player, hp);
    }

    public static Move RequestMove(Player player, int step) {
        return new Move(player, step);
    }
}
