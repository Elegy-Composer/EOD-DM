package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.GuerrillaShooterSummon;
import eod.exceptions.NotSupportedException;
import eod.warObject.character.abstraction.assaulter.Shooter;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class GuerrillaShooter extends Shooter {
    public GuerrillaShooter(Player player) {
        super(player, 1, 2, 1, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        return new GuerrillaShooterSummon(player);
    }

    @Override
    public String getName() {
        return "游擊隊槍手";
    }

    @Override
    public void attack() {
        RequestRegionalAttack(player, attack).from(this).to(player.getBoard().getSurrounding(position, 1), 1);
    }
}
