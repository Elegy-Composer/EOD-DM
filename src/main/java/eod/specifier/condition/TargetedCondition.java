package eod.specifier.condition;

import eod.Character;

import java.util.Arrays;

public class TargetedCondition implements Condition{

    @Override
    public Character[] filter(Character[] characters) {
        return Arrays.stream(characters)
                .filter(character -> character.isTargeted)
                .toArray(Character[]::new);
    }
}
