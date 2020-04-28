package eod;

public class Character implements WarObject {
    private Player player;
    public boolean isAttacked = false;

    public Character(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
