package eod.snapshots;

import eod.characters.Character;
import eod.characters.Status;

import java.util.Arrays;

public class BoardSnapshot {
    private Character[][] allCharacter;

    public BoardSnapshot(Character[][] allCharacter) {
        this.allCharacter = allCharacter;
    }

    public Character[] getCharactersBeingAttacked() {
        return Arrays.stream(allCharacter)
                .flatMap(Arrays::stream)
                .filter(character -> character.hasStatus(Status.ATTACKED))
                .toArray(Character[]::new);
    }

    public Character[][] getAllCharacter() {
        return allCharacter;
    }
}
