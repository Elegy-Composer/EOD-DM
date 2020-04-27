package eod;

public class Leader implements WarObject, GameObject {
    private int hp;

    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public void teardown() {
        //doesn't have to do anything
    }
}
