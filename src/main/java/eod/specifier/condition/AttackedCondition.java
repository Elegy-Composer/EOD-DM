package eod.specifier.condition;

import eod.characters.Character;
import eod.characters.Status;

import java.util.Arrays;

public class AttackedCondition implements Condition {

    @Override
    public Character[] filter(Character[] characters) {
        return Arrays.stream(characters)
                .filter(character -> character.hasStatus(Status.ATTACKED))
                .toArray(Character[]::new);
    }
}
