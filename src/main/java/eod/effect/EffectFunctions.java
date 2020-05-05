package eod.effect;

import eod.Player;

//Effect functions should only be used by effect and its subclass
public class EffectFunctions {
    public static Heal RequestHeal(Player player, int hp) {
        return new Heal(player, hp);
    }

    public static Move RequestMove(Player player, int step) {
        return new Move(player, step);
    }

    public static Move RequestMove(Player player) {
        return new Move(player);
    }

    public static DirectAttack RequestDirectAttack(Player player, int hp) {
        return new DirectAttack(player, hp);
    }

    public static RegionalAttack RequestRegionalAttack(Player player, int hp) {
        return new RegionalAttack(player, hp);
    }
}
