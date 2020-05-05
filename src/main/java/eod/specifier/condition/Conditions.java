package eod.specifier.condition;

import eod.characters.Character;
import eod.Player;

public class Conditions {
    public static AttackedCondition Injured() {
        return new AttackedCondition();
    }
    public static TargetedCondition Targeted() {
        return new TargetedCondition();
    }

    public static OwnedCondition OwnedBy(Player player) {
        return new OwnedCondition(player);
    }

    public static BelongCondition Being(Class<? extends Character> type) {
        return new BelongCondition(type);
    }

    public static InAttackRangeCondition InRangeOf(Character center) {
        return new InAttackRangeCondition(center);
    }
}
