package eod.card.concrete.attack;

import eod.Player;
import eod.card.abstraction.Card;
import eod.Party;
import eod.card.abstraction.action.AttackCard;
import eod.characters.abstraction.disturber.Sniper;
import eod.effect.DirectAttack;
import eod.specifier.Accessing;
import eod.Character;

import java.util.Random;

import static eod.effect.EffectFunctions.RequestDirectAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class Snipe extends AttackCard {
    // TODO: fix the direct attack issues
    public Snipe(Player p) {
        super(p, 2);
    }

    @Override
    public Card copy() {
        return new Snipe(player);
    }

    @Override
    public void attack() {
        Accessing characters = Character(player.getBoard());
        Character[] ownedSnipers = characters.which(OwnedBy(player)).which(Being(Sniper.class)).get();
        DirectAttack directAttack = RequestDirectAttack(player, 8).from(ownedSnipers);
        directAttack.allowCondition(moreThanTwoSnipers(ownedSnipers))
                .willConditionSuccess(canSuccess(ownedSnipers))
                .to(characters.which(OwnedBy(rival)).which(InRangeOf(directAttack.attacker())).get());
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
