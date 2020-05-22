package eod.specifier.condition;

import eod.Party;
import eod.Player;
import eod.warObject.CanAttack;
import eod.warObject.Status;

import java.awt.*;
import java.util.ArrayList;

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

    public static InPointCollectionCondition InPoints(ArrayList<Point> points) {
        return new InPointCollectionCondition(points);
    }

    public static InAttackRangeCondition InRangeOf(CanAttack center) {
        return new InAttackRangeCondition(center);
    }

    public static ExcludeStatusCondition WithoutStatus(Status status) {
        return new ExcludeStatusCondition(status);
    }

    public static PartyCondition InParty(Party party) {
        return new PartyCondition(party);
    }
}
