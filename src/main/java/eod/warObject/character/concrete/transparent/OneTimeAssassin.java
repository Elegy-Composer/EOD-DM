package eod.warObject.character.concrete.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.OneTimeAssassinSummon;
import eod.exceptions.NotSupportedException;
import eod.warObject.character.abstraction.assaulter.Assassin;

public class OneTimeAssassin extends Assassin {
    // TODO: remove or change the name
    public OneTimeAssassin(Player player) {
        super(player, 1,4, Party.TRANSPARENT);
    }

    @Override
    public SummonCard getSummonCard() {
        return new OneTimeAssassinSummon(player);
    }

    @Override
    public String getName() {
        return "單次僱用殺手";
    }

    @Override
    public void attack() {

    }
}
