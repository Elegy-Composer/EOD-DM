package eod.warObject;

public interface Damageable {
    void damage(int hp);
    void addStatus(Status s);
    boolean hasStatus(Status s);
    void removeStatus(Status s);
    void heal(int hp);
    void die();
}
