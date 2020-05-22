package eod.card.concrete.attack.transparent;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.AttackCard;
import eod.effect.Attack;
import eod.effect.RegionalAttack;
import eod.specifier.Accessing;
import eod.warObject.Damageable;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.disturber.Sniper;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.*;

public class Snipe extends AttackCard {

    @Override
    public Card copy() {
        Card c = new Snipe();
        c.setPlayer(player);
        return c;
    }

    @Override
    public Attack attack() {
        Accessing objects = WarObject(player.getBoard());
        WarObject[] ownedSnipers = objects.which(OwnedBy(player)).which(Being(Sniper.class)).get();
        RegionalAttack attack = RequestRegionalAttack(8).from(player, ownedSnipers);
        return attack.to(player, objects.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).which(Being(Damageable.class)).get());
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public String getName() {
        return "狙殺";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
