package eod.effect;

import eod.Character;
import eod.GameObject;
import eod.Player;

public class Heal implements Effect, GameObject {
    private int hp;
    private Player player;

    public Heal(Player player, int hp) {
        this.hp = hp;
        this.player = player;
    }

    public void from(Character[] characters) {
        Character selected = askToSelect(player, characters);
        selected.heal(hp);
    }

    @Override
    public void teardown() {
        player = null;
    }
}
