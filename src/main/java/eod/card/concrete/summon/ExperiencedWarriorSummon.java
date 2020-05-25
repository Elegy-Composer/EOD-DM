package eod.card.concrete.summon;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.red.ExperiencedWarrior;

import static eod.effect.EffectFunctions.Summon;

public class ExperiencedWarriorSummon extends SummonCard {
    public ExperiencedWarriorSummon() {
        super(3, SummonCardType.TOKEN);
    }

    @Override
    public Summon summonEffect() {
        return Summon(new ExperiencedWarrior(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new ExperiencedWarriorSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 身經百戰的戰士";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
