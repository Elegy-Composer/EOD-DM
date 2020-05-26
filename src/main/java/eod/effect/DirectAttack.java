package eod.effect;

import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectAttack extends Attack {
    // This class should be used only in direct attacks.
    // If there's a ranged attack, use RegionalAttack.
    private ArrayList<Damageable> targets;

    protected DirectAttack(int hp) {
        super(hp);
        targets = new ArrayList<>();
    }

    public DirectAttack from(Player player, WarObject[] objects) {
        attacker = (CanAttack) askToSelectOneFrom(player, objects);
        return this;
    }

    public DirectAttack from(WarObject object) {
        attacker = (CanAttack) object;
        return this;
    }

    public DirectAttack realDamage() {
        param.realDamage = true;
        return this;
    }

    public DirectAttack to(Player player, WarObject[] objects) {
        targets.add((Damageable) askToSelectOneFrom(player, objects));
        return this;
    }

    public DirectAttack to(WarObject object) {
        targets.add((Damageable) object);
        return this;
    }

    public DirectAttack toAll(Damageable[] targets) {
        this.targets.addAll(Arrays.asList(targets));
        return this;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        Player player = castExecutor(executor);
        for(Damageable target: targets) {
            try {
                affected.addAll(player.attack(attacker, target, param));
            } catch (NotSupportedException e) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public HandlerType desiredHandlerType() {
        return HandlerType.Rival;
    }
}
