package eod.warObject;

import eod.warObject.character.Status;

public interface Touchable {
    void damage(int hp);
    void addStatus(Status s);
    boolean hasStatus(Status s);
    void removeStatus(Status s);
    void heal(int hp);
}
