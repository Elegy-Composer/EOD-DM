package eod.warObject.character.concrete.blue;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.RepairmanSummon;
import eod.effect.Summon;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.character.abstraction.Machine;
import eod.warObject.character.abstraction.supporter.Sapper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.*;

public class Repairman extends Sapper {
    public Repairman(Player player) {
        super(player, 2,1, Party.BLUE);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard card = new RepairmanSummon();
        card.setPlayer(player);
        return card;
    }

    @Override
    public String getName() {
        return "維修技師";
    }

    @Override
    public void attack() {
        Arrays.stream(WarObject(player.getBoard())
                .which(InRangeOf(this))
                .which(OwnedBy(player))
                .which(Being(Machine.class)).get()).forEach(object -> {
                    Machine machine = (Machine) object;
                    machine.addHealth(1);
                    machine.addAttack(1);
        });

        RequestRegionalAttack(player, attack).from(this).to(getAttackRange());
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }
}
