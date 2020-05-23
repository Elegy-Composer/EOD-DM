package eod.warObject;

import eod.param.DamageParam;

public interface Damageable {
    void damage(DamageParam param);
    void attacked(CanAttack attacker, DamageParam param);
    void heal(int hp);
    void addHealth(int hp);
    void die();
    int getHp();
    CanAttack getAttacker();
}
