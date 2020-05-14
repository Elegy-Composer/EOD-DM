package eod.specifier.condition;

import eod.Player;
import eod.warObject.CanAttack;

public class Conditions {
    public static AttackedCondition Injured() {
        return new AttackedCondition();
    }

    public static OwnedCondition OwnedBy(Player player) {
        return new OwnedCondition(player);
    }

    public static BelongCondition Being(Class type) {
        return new BelongCondition(type);
    }

    public static InAttackRangeCondition InRangeOf(CanAttack center) {
        return new InAttackRangeCondition(center);
    }
}
