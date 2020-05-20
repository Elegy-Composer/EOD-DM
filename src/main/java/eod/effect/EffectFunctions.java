package eod.effect;

import eod.warObject.WarObject;

//Effect functions should only be used by effect and its subclass
public class EffectFunctions {
    public static Heal RequestHeal(int hp) {
        return new Heal(hp);
    }

    public static Move RequestMove(int step) {
        return new Move(step);
    }

    public static Move RequestMove() {
        return new Move();
    }

    public static DirectAttack RequestDirectAttack(int hp) {
        return new DirectAttack(hp);
    }

    public static RegionalAttack RequestRegionalAttack(int hp) {
        return new RegionalAttack(hp);
    }

    public static Summon Summon(WarObject object) {
        return new Summon(object);
    }
}
