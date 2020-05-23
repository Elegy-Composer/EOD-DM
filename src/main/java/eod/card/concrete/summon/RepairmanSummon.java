package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.blue.Repairman;

import static eod.effect.EffectFunctions.Summon;

public class RepairmanSummon extends SummonCard {
    public RepairmanSummon() {
        super(2, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        Summon(player, new Repairman(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new RepairmanSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 維修技師";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
