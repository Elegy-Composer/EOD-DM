package eod.specifier.condition;

import eod.Character;

public interface Condition {
    public Character[] filter(Character[] characters);
}
