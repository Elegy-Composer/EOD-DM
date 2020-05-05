package eod.specifier.condition;

import eod.characters.Character;
import eod.characters.Status;

import java.util.Arrays;

public class TargetedCondition implements Condition{

    @Override
    public Character[] filter(Character[] characters) {
        return Arrays.stream(characters)
                .filter(character -> character.status.contains(Status.TARGETED))
                .toArray(Character[]::new);
    }
}
