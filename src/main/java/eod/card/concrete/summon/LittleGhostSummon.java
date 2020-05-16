package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.warObject.character.concrete.red.LittleGhost;

import static eod.effect.EffectFunctions.Summon;

public class LittleGhostSummon extends SummonCard {
    public LittleGhostSummon(Player p) {
        super(p);
    }

    @Override
    public void summon() {
        Summon(player, new LittleGhost(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new LittleGhostSummon(player);
    }

    @Override
    public int getCost() {
        return 0;
        // TODO: ask Spacezipper about the details of the ghosts.
    }

    @Override
    public String getName() {
        return "召喚 小亡靈";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
