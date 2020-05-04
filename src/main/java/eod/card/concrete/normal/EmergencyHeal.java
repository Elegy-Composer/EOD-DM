package eod.card.concrete.normal;

import eod.Player;
import eod.Party;
import eod.card.abstraction.action.NormalCard;

import static eod.specifier.condition.Conditions.Injured;
import static eod.specifier.timing.Timings.LastEnemyTurn;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.effect.EffectFunctions.*;

public class EmergencyHeal extends NormalCard {

    public EmergencyHeal(Player p) {
        super(p, 1);
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "緊急治療";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }

    @Override
    public void applyEffect() {
        RequestHeal(player, 3).from(Character(player.getBoard()).which(Injured()).in(LastEnemyTurn()).get());
    }

    @Override
    public EmergencyHeal copy() {
        return new EmergencyHeal(player);
    }
}
