package eod.specifier.condition;

import eod.Character;
import eod.Player;

import java.util.Arrays;

public class OwnedCondition implements Condition {
    private Player player;

    public OwnedCondition(Player player) {
        this.player = player;
    }

    @Override
    public Character[] filter(Character[] characters) {
        return Arrays.stream(characters)
                .filter(character -> character.getPlayer() == player)
                .toArray(Character[]::new);
    }
}
