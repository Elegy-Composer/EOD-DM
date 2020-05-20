package eod.specifier.condition;

import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;

import java.util.Arrays;

public class ExcludeStatusCondition implements Condition{
    private Status status;

    public ExcludeStatusCondition(Status status) {
        this.status = status;
    }

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(object -> !object.hasStatus(status))
                .toArray(WarObject[]::new);
    }
}
