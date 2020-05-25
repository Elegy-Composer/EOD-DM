package eod.effect;

import eod.Player;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

public class Damage implements Effect {

    private HandlerType handlerType;
    private Damageable target;
    private int hp;

    public Damage(int hp, HandlerType handlerType) {
        this.hp = hp;
        this.handlerType = handlerType;
    }

    public Damage on(Damageable victim) {
        target = victim;
        return this;
    }

    public Damage onOneOf(Player player, WarObject[] targets) {
        Damageable target = (Damageable) askToSelectOneFrom(player, targets);
        this.target = target;
        return this;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        if(handlerType == HandlerType.Game) {
            throw new WrongExecutorException("A damage should be executed by a player");
        }
        Player player = castExecutor(executor);
        player.damage(target, hp);
    }

    @Override
    public HandlerType desiredHandlerType() {
        return handlerType;
    }
}
