package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.ExperiencedWarriorSummon;
import eod.warObject.character.abstraction.assaulter.Fighter;

public class ExperiencedWarrior extends Fighter {
    public ExperiencedWarrior(Player player) {
        super(player, 5, 5, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new ExperiencedWarriorSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "身經百戰的戰士";
    }
}
