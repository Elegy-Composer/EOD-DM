package eod.card.concrete.normal;

import eod.Player;
import eod.Party;
import eod.card.abstraction.action.NormalCard;
import eod.warObject.character.abstraction.Character;

import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.Injured;
import static eod.specifier.timing.Timings.LastEnemyTurn;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.effect.EffectFunctions.*;

public class EmergencyHeal extends NormalCard {
    public EmergencyHeal() {
        super(1);
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
        RequestHeal(player, 3)
                .to(
                    WarObject(player.getBoard()).which(Being(Character.class)).which(Injured()).in(LastEnemyTurn()).get()
                );
    }

    @Override
    public EmergencyHeal copy() {
        EmergencyHeal card = new EmergencyHeal();
        card.setPlayer(player);
        return card;
    }
}
