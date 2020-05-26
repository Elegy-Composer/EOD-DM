package eod.effect;

import eod.Player;
import eod.warObject.Damageable;

public class IncreaseHealth implements Effect {

    private int hp;
    private Damageable target;

    IncreaseHealth(int hp) {
        this.hp = hp;
    }

    public IncreaseHealth to(Damageable object) {
        this.target = object;
        return this;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        Player owner = castExecutor(executor);
        owner.increaseHealth(target, hp);
    }

    @Override
    public HandlerType desiredHandlerType() {
        return HandlerType.Owner;
    }
}
