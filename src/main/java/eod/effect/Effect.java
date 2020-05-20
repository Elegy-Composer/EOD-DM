package eod.effect;

import eod.Player;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

//Base interface of all effect
public interface Effect {

    void action(EffectExecutor executor) throws WrongExecutorException;
    HandlerType desiredHandlerType();

    default WarObject askToSelectOneFrom(Player player, WarObject[] from) {
        return player.selectObject(from);
    }

    default WarObject[] askToSelectMultipleFrom(Player player, WarObject[] from, int number) {
        return player.selectMultipleObject(from, number);
    }

    default Point askToSelectOneFrom(Player player, ArrayList<Point> candidates) {
        return player.selectPosition(candidates);
    }

    enum HandlerType {
        Owner, Game, Rival
    }

    class WrongExecutorException extends RuntimeException {
        public WrongExecutorException() {
            super("Executor is not compatible with desiredHandlerType");
        }
    }
}
