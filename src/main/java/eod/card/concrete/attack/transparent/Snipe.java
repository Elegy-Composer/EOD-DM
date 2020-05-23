package eod.card.concrete.attack.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.AttackCard;
import eod.effect.RegionalAttack;
import eod.specifier.Accessing;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.disturber.Sniper;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.*;

public class Snipe extends AttackCard {
    public Snipe() {
        super(5);
    }

    @Override
    public Card copy() {
        Card c = new Snipe();
        c.setPlayer(player);
        return c;
    }

    @Override
    public void attack() {
        Accessing objects = WarObject(player.getBoard());
        Accessing ownedSnipers = objects.which(OwnedBy(player)).which(Being(Sniper.class));
        RegionalAttack attack = RequestRegionalAttack(player, decideAttack(ownedSnipers.get())).from(ownedSnipers.which(WithoutStatus(Status.CANT_ATTACK)).get());
        attack.to(objects.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).which(Being(Damageable.class)).which(WithoutStatus(Status.SNEAK)).get());
    }

    private int decideAttack(WarObject[] snipers) {
        if(snipers.length >= 2) {
            return 8;
        } else {
            return 6;
        }
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
