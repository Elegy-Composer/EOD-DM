package eod.warObject.leader.red;

import eod.Player;
import eod.card.abstraction.action.NormalCard;
import eod.card.concrete.normal.EquivalentExchange;
import eod.warObject.leader.Leader;

import java.util.ArrayList;

public class Sundar extends Leader {

    public Sundar(Player player) {
        super(player, 20);
    }

    @Override
    public void attack() {
        player.selectPosition(player.getBoard().getSurroundingEmpty(position, 1));
        // TODO: add summon event
    }

    @Override
    protected ArrayList<NormalCard> generateSpecialOrder() {
        ArrayList<NormalCard> deck = new ArrayList<>();
        deck.add(new EquivalentExchange(player));
        // TODO: add another special order
        return deck;
    }
}
