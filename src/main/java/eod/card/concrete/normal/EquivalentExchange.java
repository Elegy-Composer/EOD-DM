package eod.card.concrete.normal;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;

public class EquivalentExchange extends NormalCard {
    public EquivalentExchange(Player p) {
        super(p);
    }

    @Override
    public void applyEffect() {
        player.getLeader().damage(2);
        // TODO: summon LittleGhost
    }

    @Override
    public Card copy() {
        return new EquivalentExchange(player);
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "等價交換";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
