package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.GangsterSummon;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.warObject.character.abstraction.Character;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class Gangster extends Character {
    public Gangster(Player player) {
        super(player, 1,1, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard card = new GangsterSummon();
        card.setPlayer(player);
        return card;
    }

    @Override
    public String getName() {
        return "好戰分子";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        PointParam param = new PointParam();
        param.range = 1;
        executor.tryToExecute(
                RequestRegionalAttack(attack).from(this).to(player.getFront(position, param))
        );
    }
}
