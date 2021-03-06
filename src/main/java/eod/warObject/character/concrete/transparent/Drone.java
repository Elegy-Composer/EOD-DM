package eod.warObject.character.concrete.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.transparent.DroneSummon;
import eod.param.PointParam;
import eod.effect.EffectExecutor;
import eod.warObject.Damageable;
import eod.warObject.character.abstraction.Machine;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class Drone extends Machine {

    public Drone(Player player) {
        super(player, 2, 3, Party.TRANSPARENT);
    }

    @Override
    public String getName() {
        return "無人機";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        if(WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Drone.class)).which(InRangeOf(this)).get().length >= 1) {
            attack += 2;
        }
        executor.tryToExecute(
                RequestRegionalAttack(attack)
                        .from(this)
                        .to(player, WarObject(player.getBoard())
                        .which(OwnedBy(player.rival()))
                        .which(Being(Damageable.class))
                        .which(InRangeOf(this)).get())
        );

        afterAttack();
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new DroneSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }
}
