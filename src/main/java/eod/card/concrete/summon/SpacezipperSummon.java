package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.transparent.Spacezipper;

import static eod.effect.EffectFunctions.Summon;

public class SpacezipperSummon extends SummonCard {
    public SpacezipperSummon(Player p) {
        super(p, SummonCardType.SPECIAL);
    }

    @Override
    public void summon() {
        Summon(player, new Spacezipper(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new SpacezipperSummon(player);
    }

    @Override
    public int getCost() {
        return 7;
    }

    @Override
    public String getName() {
        return "召喚 空間之鏈-圭月";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
