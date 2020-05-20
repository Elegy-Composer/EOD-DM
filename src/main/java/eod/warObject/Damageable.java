package eod.warObject;

public interface Damageable {
    void realDamage(int hp);
    void damage(int hp);
    void attacked(CanAttack attacker, int hp);
    void heal(int hp);
    void addHealth(int hp);
    void die();
    int getHp();
    CanAttack getAttacker();
}
