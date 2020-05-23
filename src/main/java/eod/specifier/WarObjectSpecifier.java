package eod.specifier;

import eod.Gameboard;
import eod.warObject.Damageable;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.util.Arrays;

public class WarObjectSpecifier {
    public static Accessing WarObject(Gameboard gameboard) {
        WarObject[][] objects = gameboard.takeSnapshot().getAllObjects();

        return new Accessing(Arrays.stream(objects)
                .flatMap(Arrays::stream)
                .toArray(WarObject[]::new));
    }
}
