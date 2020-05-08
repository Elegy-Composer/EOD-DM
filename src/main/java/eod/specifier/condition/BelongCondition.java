package eod.specifier.condition;

import eod.warObject.WarObject;
import eod.warObject.character.Character;

import java.util.Arrays;

public class BelongCondition implements Condition{
    private Class type;

    public BelongCondition(Class type) {
        this.type = type;
    }

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(character -> type.isInstance(character))
                .toArray(WarObject[]::new);
    }
}
