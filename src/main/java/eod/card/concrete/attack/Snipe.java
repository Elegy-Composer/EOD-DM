package eod.card.concrete.attack;

import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.CardParty;
import eod.card.abstraction.action.AttackCard;
import eod.characters.Sniper;
import eod.specifier.Accessing;
import eod.Character;

import java.util.Random;

import static eod.effect.EffectFunctions.RequestAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class Snipe extends AttackCard {
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
        RequestAttack(player, 8)
                .from(ownedSnipers)
                .allowCondition(moreThanTwoSnipers(ownedSnipers))
                .willConditionSuccess(canSuccess(ownedSnipers))
                .to(characters.which(OwnedBy(rival)).get());
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
    public CardParty getParty() {
        return CardParty.TRANSPARENT;
    }
}
