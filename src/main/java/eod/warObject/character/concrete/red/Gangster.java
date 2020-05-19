package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.GangsterSummon;
import eod.exceptions.NotSupportedException;
import eod.param.PointParam;
import eod.warObject.character.abstraction.Character;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class Gangster extends Character {
    public Gangster(Player player) {
        super(player, 1,1, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        return new GangsterSummon(player);
    }

    @Override
    public String getName() {
        return "好戰分子";
    }

    @Override
    public void attack() {
        PointParam param = new PointParam();
        param.range = 1;
        RequestRegionalAttack(player, attack).from(this).to(player.getFront(position, param));
    }
}
