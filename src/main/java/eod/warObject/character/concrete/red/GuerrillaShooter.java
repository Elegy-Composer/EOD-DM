package eod.warObject.character.concrete.red;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.GuerrillaShooterSummon;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.assaulter.Shooter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class GuerrillaShooter extends Shooter {
    private int specialEffectTimes = 0;
    public GuerrillaShooter(Player player) {
        super(player, 1, 2, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new GuerrillaShooterSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "游擊隊槍手";
    }

    @Override
    public void attack() {
        super.attack();
        ArrayList<Damageable> affected = RequestRegionalAttack(player, attack).from(this).to(getAttackRange(), 1).getAffected();
        if(affected.size() > 0 && affected.get(0).getHp() <= 0) {
            specialEffectTimes++;
            move();
        }
        if(specialEffectTimes >= 2) {
            specialEffectTimes -= 2;
            attack();
        }

        afterAttack();
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }
}
