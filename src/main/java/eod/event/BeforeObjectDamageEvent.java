package eod.event;

import eod.param.DamageParam;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

public class BeforeObjectDamageEvent implements Event {
    private Damageable victim;
    private DamageParam param;

    public BeforeObjectDamageEvent(Damageable victim, DamageParam param) {
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
