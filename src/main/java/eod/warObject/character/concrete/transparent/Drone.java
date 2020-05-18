package eod.warObject.character.concrete.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.DroneSummon;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.character.abstraction.Machine;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class Drone extends Machine implements CanAttack, Damageable {

    public Drone(Player player) {
        super(player, 2, 3, 1, Party.TRANSPARENT);
    }

    @Override
    public String getName() {
        return "無人機";
    }

    @Override
    public void attack() {
        if(WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Drone.class)).which(InRangeOf(this)).get().length >= 1) {
            attack += 2;
        }
        RequestRegionalAttack(player, attack).from(this).to(WarObject(player.getBoard())
                .which(OwnedBy(player.rival()))
                .which(Being(Damageable.class))
                .which(InRangeOf(this)).get());
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
    public void teardown() {
        super.teardown();
    }
}
