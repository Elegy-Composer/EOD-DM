package eod.specifier.condition;

import eod.warObject.WarObject;
import eod.warObject.character.Character;

public interface Condition {
    WarObject[] filter(WarObject[] objects);
}
