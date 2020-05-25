package eod.effect;

import eod.Game;
import eod.Player;
import eod.exceptions.NotSupportedException;
import eod.warObject.CanAttack;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.ArrayList;

public class RegionalAttack extends Attack {
    // This class should be used only in regional attacks.
    // If there's a direct attack, use DirectAttack.
    private ArrayList<Point> targets;

    public RegionalAttack(int hp) {
        super(hp);
        targets = new ArrayList<>();
    }

    public RegionalAttack from(Player player, WarObject[] objects) {
        attacker = (CanAttack) askToSelectOneFrom(player, objects);
        return this;
    }

    public RegionalAttack from(WarObject object) {
        attacker = (CanAttack) object;
        return this;
    }

    public RegionalAttack realDamage() {
        param.realDamage = true;
        return this;
    }

    public RegionalAttack to(ArrayList<Point> targets) {
        this.targets = targets;
        return this;
    }

    public RegionalAttack to(Player player, ArrayList<Point> candidates, int number) {
        if(number >= candidates.size()) {
            return to(candidates);
        }
        ArrayList<Point> targets = new ArrayList<>();
        for(int i = 0;i < number;i++) {
            Point target = askToSelectOneFrom(player, candidates);
            targets.add(target);
            candidates.remove(target);
        }
        return to(targets);
    }

    public RegionalAttack to(Player player, WarObject[] candidates) {
        WarObject target = askToSelectOneFrom(player, candidates);
        ArrayList<Point> singleTarget = new ArrayList<>();
        singleTarget.add(target.position);
        return to(singleTarget);
    }

    public RegionalAttack to(WarObject target) {
        ArrayList<Point> singleTarget = new ArrayList<>();
        singleTarget.add(target.position);
        return to(singleTarget);
    }

    protected ArrayList<Point> getTargets() {
        return targets;
    }

    @Override
    public void action(EffectExecutor executor) throws WrongExecutorException {
        Game game = castExecutor(executor);
        try {
            affected.addAll(game.damage(attacker, targets, param));
        } catch (NotSupportedException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public HandlerType desiredHandlerType() {
        //The reason using Game is that once a RegionalAttack specify its attack point
        //the Character standing on the point will be damaged, no matter who is its owner.
        return HandlerType.Game;
    }
}
