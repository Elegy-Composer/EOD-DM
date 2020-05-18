package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.warObject.character.concrete.transparent.Drone;

import static eod.effect.EffectFunctions.Summon;

public class DroneSummon extends SummonCard {
    public DroneSummon(Player p) {
        super(p);
    }

    @Override
    public void summon() {
        Summon(player, new Drone(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new DroneSummon(player);
    }

    @Override
    public int getCost() {
        return 3;
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
