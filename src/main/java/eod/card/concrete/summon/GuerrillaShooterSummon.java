package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.warObject.character.concrete.red.GuerrillaShooter;

import static eod.effect.EffectFunctions.Summon;

public class GuerrillaShooterSummon extends SummonCard {
    public GuerrillaShooterSummon(Player p) {
        super(p);
    }

    @Override
    public void summon() {
        Summon(player, new GuerrillaShooter(player)).from(player.getBaseEmpty());
    }

    @Override
    public Card copy() {
        return new GuerrillaShooterSummon(player);
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public String getName() {
        return "召喚 游擊隊槍手";
    }

    @Override
    public Party getParty() {
        return null;
    }
}
