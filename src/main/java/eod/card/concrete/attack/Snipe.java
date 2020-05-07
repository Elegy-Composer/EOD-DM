package eod.card.concrete.attack;

import eod.Player;
import eod.card.abstraction.Card;
import eod.Party;
import eod.card.abstraction.action.AttackCard;
import eod.specifier.condition.BelongCondition;
import eod.warObject.Touchable;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.disturber.Sniper;
import eod.effect.RegionalAttack;
import eod.specifier.Accessing;
import eod.warObject.character.Character;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class Snipe extends AttackCard {
    // TODO: fix the direct attack issues
    public Snipe(Player p) {
        super(p);
    }

    @Override
    public Card copy() {
        return new Snipe(player);
    }

    @Override
    public void attack() {
        Accessing objects = WarObject(player.getBoard());
        WarObject[] ownedSnipers = objects.which(OwnedBy(player)).which(Being(Sniper.class)).get();
        RegionalAttack attack = RequestRegionalAttack(player, 8).from(ownedSnipers);
        attack.to(objects.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).which(Being(Touchable.class)).get());
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
