package eod.effect;

import eod.Player;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

//Base interface of all effect
//TODO: find a way to include passive effect (i.e. a effect (or action) that is activated by
// rule, due to the occurrence of an event
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
        //Note: Handler should be the one who has the right to manipulate the selected WarObject

        //For example, an DirectAttack effect's handler type should be Rival
        //because it's your rival player the one who should change its WarObject(Damaged)'s property.
        //On the other hand, a heal effect's handler type should be Owner
        //because it's you the one who should change your WarObject(Healed)'s property.
        Owner,
        Game,
        Rival
    }

    default <T> T castExecutor(EffectExecutor executor) throws WrongExecutorException {
        try {
            return (T) executor;
        } catch (ClassCastException e) {
            throw new WrongExecutorException();
        }
    }

    class WrongExecutorException extends RuntimeException {
        public WrongExecutorException() {
            super("Executor is not compatible with desiredHandlerType");
        }

        public WrongExecutorException(String message) {
            super(message);
        }
    }
}
