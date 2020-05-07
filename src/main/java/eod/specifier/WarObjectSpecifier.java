package eod.specifier;

import eod.Game;
import eod.warObject.Touchable;
import eod.warObject.WarObject;
import eod.warObject.character.Character;
import eod.Gameboard;

import java.util.Arrays;

public class WarObjectSpecifier {
    public static Accessing Character(Gameboard gameboard) {
        WarObject[][] objects = gameboard.snapshot().getAllObjects();

        return new Accessing(Arrays.stream(objects)
                .flatMap(Arrays::stream)
                .filter(object -> object instanceof Character)
                .toArray(WarObject[]::new));
    }

    public static Accessing WarObject(Gameboard gameboard) {
        WarObject[][] objects = gameboard.snapshot().getAllObjects();

        return new Accessing(Arrays.stream(objects)
                .flatMap(Arrays::stream)
                .toArray(WarObject[]::new));
    }

    public static Accessing Touchable(Gameboard gameboard) {
        WarObject[][] objects = gameboard.snapshot().getAllObjects();

        return new Accessing(Arrays.stream(objects)
                .flatMap(Arrays::stream)
                .filter(object -> object instanceof Touchable)
                .toArray(WarObject[]::new));
    }
}
