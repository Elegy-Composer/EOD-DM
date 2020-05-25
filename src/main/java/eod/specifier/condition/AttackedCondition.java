package eod.specifier.condition;

import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;

import java.util.Arrays;

public class AttackedCondition implements Condition {

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(object -> object instanceof Damageable)
                .filter(character -> character.hasStatus(Status.ATTACKED))
                .toArray(WarObject[]::new);
    }
}
