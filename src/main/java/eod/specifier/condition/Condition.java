package eod.specifier.condition;

import eod.characters.Character;

public interface Condition {
    Character[] filter(Character[] characters);
}
