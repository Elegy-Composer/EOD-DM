package eod.specifier;

import eod.characters.Character;
import eod.Gameboard;

import java.util.Arrays;

public class WarObjectSpecifier {
    public static Accessing Character(Gameboard gameboard) {
        Character[][] characters = gameboard.snapshot().getAllCharacter();

        return new Accessing(Arrays.stream(characters)
                .flatMap(Arrays::stream)
                .toArray(Character[]::new));
    }
}
