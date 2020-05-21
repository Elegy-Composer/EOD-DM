package eod.warObject.character.concrete.red;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.ScavengerSummon;
import eod.exceptions.NotSupportedException;
import eod.param.AttackParam;
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
    public void attack() {
        super.attack();
        RequestRegionalAttack(player, attack).from(this).to(getAttackRange(), 1);
    }

    @Override
    public ArrayList<Damageable> attack(ArrayList<Point> targets, AttackParam param) {
        int hp = param.hp;
        ArrayList<Damageable> affected = new ArrayList<>();
        Gameboard gameboard = player.getBoard();
        for(Point p:targets) {
            try {
                Damageable target = gameboard.getObjectOn(p.x, p.y);
                if(target.getHp() <= 2) {
                    target.die();
                } else if(param.realDamage) {
                    target.realDamage(hp);
                } else {
                    target.attacked(this, hp);
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
