package eod.effect;

import eod.GameObject;
import eod.Player;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Heal implements Effect, GameObject {
    private int hp;
    private int number;
    private ArrayList<Damageable> objects;

    public Heal(int hp) {
        this.hp = hp;
        objects = new ArrayList<>();
    }

    public Heal to(Player player, WarObject[] objects) {
        number = 1;
        this.objects.add((Damageable) askToSelectOneFrom(player, objects));
        return this;
    }

    public Heal to(Player player, WarObject[] objects, int number) {
        Damageable[] selectedGroup = (Damageable[]) askToSelectMultipleFrom(player, objects, number);
        this.number = number;
        this.objects.addAll(Arrays.asList(selectedGroup));
        return this;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        try {
            Player owner = (Player) executor;
            for(int i = 0; i < number; i++) {
                owner.heal(objects.get(i), hp);
            }
        } catch (ClassCastException e) {
            throw new WrongExecutorException();
        }

    }

    @Override
    public HandlerType desiredHandlerType() {
        return HandlerType.Owner;
    }


    @Override
    public void teardown() {
        objects.clear();
        objects = null;
    }
}
