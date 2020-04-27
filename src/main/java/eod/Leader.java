package eod;

public class Leader implements WarObject {
    private int hp;

    public boolean isAlive() {
        return hp > 0;
    }
}
