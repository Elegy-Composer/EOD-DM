package eod.warObject.character.concrete.red;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.ScavengerSummon;
import eod.effect.EffectExecutor;
import eod.exceptions.NotSupportedException;
import eod.param.AttackParam;
import eod.param.DamageParam;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class Scavenger extends Character {
    public Scavenger(Player player) {
        super(player, 2, 1, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new ScavengerSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "清道夫";
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().get4Ways(position, param);
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        executor.tryToExecute(
            RequestRegionalAttack(attack).from(this).to(player, getAttackRange(), 1)
        );

        afterAttack();
    }



    @Override
    public ArrayList<Damageable> attack(Gameboard gameboard, ArrayList<Point> targets, AttackParam param) {
        int a;
        if(hasStatus(Status.FURIOUS)) {
            a = param.hp *2;
        } else {
            a = param.hp;
        }
        ArrayList<Damageable> affected = new ArrayList<>();
        for(Point p:targets) {
            try {
                Damageable target = gameboard.getObjectOn(p.x, p.y);
                DamageParam dp = new DamageParam(a);
                dp.realDamage = param.realDamage;
                if(target.getHp() <= 2) {
                    target.die();
                } else {
                    target.attacked(this, dp);
                }
                affected.add(target);
                ((WarObject)target).addStatus(Status.ATTACKED);
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
        return affected;
    }
}
