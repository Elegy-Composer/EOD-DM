package eod.effect;

import eod.Player;
import eod.warObject.Status;
import eod.warObject.WarObject;

import java.util.Arrays;

public class GiveStatus implements Effect {
    private Status status;
    private Player player;

    public GiveStatus(Player player, Status status) {
        this.player = player;
        this.status = status;
    }

    public void to(WarObject object) {
        to(new WarObject[] {object});
    }

    public void to(WarObject[] objects) {
        Arrays.stream(objects).forEach(object -> object.addStatus(status));
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
