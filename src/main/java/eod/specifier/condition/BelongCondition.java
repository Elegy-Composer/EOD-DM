package eod.specifier.condition;

import eod.Character;

import java.util.Arrays;

public class BelongCondition implements Condition{
    private Class<? extends Character> type;

    public BelongCondition(Class<? extends Character> type) {
        this.type = type;
    }
    @Override
    public Character[] filter(Character[] characters) {
        return Arrays.stream(characters)
                .filter(character -> type.isInstance(character))
                .toArray(Character[]::new);
    }
}
