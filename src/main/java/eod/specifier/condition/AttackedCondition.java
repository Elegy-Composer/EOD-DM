package eod.specifier.condition;

import eod.warObject.Touchable;
import eod.warObject.WarObject;
import eod.warObject.character.Character;
import eod.warObject.character.Status;

import java.util.Arrays;

public class AttackedCondition implements Condition {

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(object -> object instanceof Touchable)
                .map(object -> (Touchable) object)
                .filter(character -> character.hasStatus(Status.ATTACKED))
                .toArray(WarObject[]::new);
    }
}
