package eod.card.concrete.summon.transparent;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.MachineSummon;
import eod.card.abstraction.summon.SummonCardType;
import eod.effect.Summon;
import eod.warObject.character.concrete.transparent.Drone;

import static eod.effect.EffectFunctions.Summon;

public class DroneSummon extends MachineSummon {
    public DroneSummon() {
        super(3, SummonCardType.TOKEN);
    }

    @Override
    public Summon summonEffect() {
         return Summon(new Drone(player)).onOnePointOf(player, player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        Card c = new DroneSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "召喚 無人機";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
