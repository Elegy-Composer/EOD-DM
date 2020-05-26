package eod.effect;

import eod.Player;
import eod.param.DamageParam;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

public class Damage implements Effect {

    private HandlerType handlerType;
    private Damageable target;
    private DamageParam param;

    Damage(DamageParam param, HandlerType handlerType) {
        this.param = param;
        this.handlerType = handlerType;
    }

    public Damage on(Damageable victim) {
        target = victim;
        return this;
    }

    public Damage onOneOf(Player player, WarObject[] targets) {
        this.target = (Damageable) askToSelectOneFrom(player, targets);
        return this;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        if(handlerType == HandlerType.Game) {
            throw new WrongExecutorException("A damage should be executed by a player");
        }
        Player player = castExecutor(executor);
        player.damage(target, param);
    }

    @Override
    public HandlerType desiredHandlerType() {
        return handlerType;
    }
}
