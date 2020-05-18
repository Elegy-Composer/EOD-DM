package eod.warObject.character.concrete.blue;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.HeavyPoliceSummon;
import eod.exceptions.NotSupportedException;
import eod.warObject.character.abstraction.Character;

public class HeavyPolice extends Character {
    public HeavyPolice(Player player) {
        super(player, 5,5,-1, Party.BLUE);
    }

    @Override
    public SummonCard getSummonCard() {
        return new HeavyPoliceSummon(player);
    }

    @Override
    public String getName() {
        return "重裝備武警";
    }

    @Override
    public void attack() {
        // TodO
    }
}
