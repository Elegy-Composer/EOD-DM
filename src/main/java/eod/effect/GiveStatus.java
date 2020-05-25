package eod.effect;

import eod.Player;
import eod.warObject.Status;
import eod.warObject.WarObject;

public class GiveStatus implements Effect {
    private Status status;
    private WarObject[] targets;
    private HandlerType handlerType;

    public GiveStatus(Status status, HandlerType handlerType) {
        this.status = status;
        this.handlerType = handlerType;
    }

    public GiveStatus to(WarObject object) {
        return to(new WarObject[] {object});
    }

    public GiveStatus to(WarObject[] objects) {
        targets = objects;
        return this;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        if(handlerType == HandlerType.Game) {
            throw new WrongExecutorException("The executor of a DirectAttack should not be a game");
        }
        Player player = castExecutor(executor);
        player.giveStatus(targets, status);
    }

    @Override
    public HandlerType desiredHandlerType() {
        return handlerType;
    }
}
