package eod.card.concrete;

import eod.Player;
import eod.card.abstraction.CardParty;
import eod.card.abstraction.NormalCard;
import eod.card.abstraction.Card;

import static eod.specifier.condition.Conditions.Injured;
import static eod.specifier.timing.Timings.LastEnemyTurn;
import static eod.card.concrete.WarObjectSpecifier.*;
import static eod.effect.EffectFunctions.*;

public class EmergencyHeal extends NormalCard {

    public EmergencyHeal(Player p) {
        super(p);
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
    public CardParty getParty() {
        return CardParty.TRANSPARENT;
    }

    @Override
    public void effect() {
        Heal(3).on(Character().which(Injured()).in(LastEnemyTurn()).get());
    }

    @Override
    public Card copy() {
        return new EmergencyHeal(player);
    }
}
