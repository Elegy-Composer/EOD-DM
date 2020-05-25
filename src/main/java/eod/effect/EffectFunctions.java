package eod.effect;

import eod.warObject.Status;
import eod.warObject.WarObject;

//EffectFunctions is used to describe a (set of) property changes of an object.
//The object changed is usually a WarObject
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
    public static DirectAttack RequestDirectAttack(int hp, Effect.HandlerType handlerType) {
        return new DirectAttack(hp, handlerType);
    }
    public static RegionalAttack RequestRegionalAttack(int hp) {
        return new RegionalAttack(hp);
    }
    public static Summon Summon(WarObject object) {
        return new Summon(object);
    }
    public static IncreaseHealth IncreaseHealth(int hp) {
        return new IncreaseHealth(hp);
    }
    public static IncreaseAttack IncreaseAttack(int hp) {
        return new IncreaseAttack(hp);
    }

    public static GiveStatus GiveStatus(Status status, Effect.HandlerType handlerType) {
        return new GiveStatus(status, handlerType);
    }
}
