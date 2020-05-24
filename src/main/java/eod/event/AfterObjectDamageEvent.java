package eod.event;

import eod.param.DamageParam;
import eod.warObject.Damageable;

public class AfterObjectDamageEvent implements Event {
    private Damageable victim;
    private DamageParam param;

    public AfterObjectDamageEvent(Damageable victim, DamageParam param) {
        this.victim = victim;
        this.param = param;
    }

    public Damageable getVictim() {
        return victim;
    }

    public DamageParam getParam() {
        return param;
    }
}
