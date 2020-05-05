package eod.card.concrete.attack;

import eod.Player;
import eod.card.abstraction.Card;
import eod.Party;
import eod.card.abstraction.action.AttackCard;
import eod.characters.abstraction.disturber.Sniper;
import eod.effect.RegionalAttack;
import eod.specifier.Accessing;
import eod.characters.Character;

import java.util.Random;

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
        Accessing characters = Character(player.getBoard());
        Character[] ownedSnipers = characters.which(OwnedBy(player)).which(Being(Sniper.class)).get();
        RegionalAttack attack = RequestRegionalAttack(player, 8).from(ownedSnipers);
        attack.allowCondition(moreThanTwoSnipers(ownedSnipers))
                .willConditionSuccess(canSuccess(ownedSnipers))
                .to(characters.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).get());
    }

    private boolean moreThanTwoSnipers(Character[] snipers) {
        return snipers.length >= 2;
    }

    private boolean canSuccess(Character[] snipers) {
        if(moreThanTwoSnipers(snipers)) {
            return true;
        } else {
            Random random = new Random();
            return random.nextBoolean();
        }
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
