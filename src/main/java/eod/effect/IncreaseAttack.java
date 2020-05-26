package eod.effect;

import eod.Player;
import eod.warObject.CanAttack;

public class IncreaseAttack implements Effect {

    private int ap;
    private CanAttack target;

    IncreaseAttack(int ap) {
        this.ap = ap;
    }

    public IncreaseAttack to(CanAttack object) {
        this.target = object;
        return this;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        Player owner = castExecutor(executor);
        owner.increaseAttack(target, ap);
    }

    @Override
    public HandlerType desiredHandlerType() {
        return HandlerType.Owner;
    }
}
