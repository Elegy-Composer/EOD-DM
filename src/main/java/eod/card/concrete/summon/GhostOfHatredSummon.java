package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.warObject.character.concrete.red.GhostOfHatred;

import static eod.effect.EffectFunctions.Summon;

public class GhostOfHatredSummon extends SummonCard {

    public GhostOfHatredSummon(Player p) {
        super(p);
    }

    @Override
    public void summon() {
        Summon(player, new GhostOfHatred(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new GhostOfHatredSummon(player);
    }

    @Override
    public int getCost() {
        return 0;
        // TODO: ask Spacezipper about the details of the ghosts
    }

    @Override
    public String getName() {
        return "召喚 怨念的亡靈";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
