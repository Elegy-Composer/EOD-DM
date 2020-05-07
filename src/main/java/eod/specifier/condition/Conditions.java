package eod.specifier.condition;

import eod.warObject.CanAttack;
import eod.warObject.WarObject;
import eod.warObject.character.Character;
import eod.Player;

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
