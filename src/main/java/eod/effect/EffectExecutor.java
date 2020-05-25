package eod.effect;

public interface EffectExecutor {
    void tryToExecute(Effect effect);

    default void tryToExecuteInSequence(Effect[] effects) {
        for(Effect effect: effects) {
            tryToExecute(effect);
        }
    }
}
