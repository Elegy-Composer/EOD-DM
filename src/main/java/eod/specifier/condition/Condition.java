package eod.specifier.condition;

import eod.warObject.WarObject;

public interface Condition {
    WarObject[] filter(WarObject[] objects);
}
