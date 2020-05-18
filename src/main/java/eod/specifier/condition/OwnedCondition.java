package eod.specifier.condition;

import eod.Player;
import eod.warObject.WarObject;

import java.util.Arrays;

public class OwnedCondition implements Condition {
    private Player player;

    public OwnedCondition(Player player) {
        this.player = player;
    }

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(object -> object.getPlayer().isPlayerA() == player.isPlayerA())
                .toArray(WarObject[]::new);
    }
}
