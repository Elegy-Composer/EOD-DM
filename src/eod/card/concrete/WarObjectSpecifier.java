package eod.card.concrete;

import eod.Character;
import eod.Gameboard;
import eod.specifier.Accessing;

import java.util.Arrays;

public class WarObjectSpecifier {
    public static Accessing Character() {
        Character[][] characters = Gameboard.getSnapshot().getAllCharacter();

        return new Accessing(Arrays.stream(characters)
                .flatMap(Arrays::stream)
                .toArray(Character[]::new));
    }
}
