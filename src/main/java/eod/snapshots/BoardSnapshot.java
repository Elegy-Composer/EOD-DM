package eod.snapshots;

import eod.warObject.WarObject;
import eod.warObject.character.Character;
import eod.warObject.character.Status;

import java.util.Arrays;

public class BoardSnapshot {
    private WarObject[][] allObjects;

    public BoardSnapshot(WarObject[][] allCharacter) {
        this.allObjects = allCharacter;
    }

    public Character[] getCharactersBeingAttacked() {
        return Arrays.stream(Arrays.stream(allObjects)
                .flatMap(Arrays::stream)
                .filter(object -> object instanceof Character)
                .toArray(Character[]::new))
                .filter(character -> character.hasStatus(Status.ATTACKED))
                .toArray(Character[]::new);
    }

    public WarObject[][] getAllObjects() {
        return allObjects;
    }
}
