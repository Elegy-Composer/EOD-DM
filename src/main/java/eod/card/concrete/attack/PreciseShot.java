package eod.card.concrete.attack;

import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.CardParty;
import eod.card.abstraction.action.AttackCard;
import eod.characters.assaulter.Shooter;
import eod.effect.Attack;
import eod.specifier.Accessing;

import static eod.specifier.WarObjectSpecifier.*;
import static eod.effect.EffectFunctions.RequestAttack;
import static eod.specifier.condition.Conditions.*;

public class PreciseShot extends AttackCard {
    public PreciseShot(Player p) {
        super(p, 1);
    }

    @Override
    public void attack() {
        Accessing characters = Character(player.getBoard());
        Attack attack = RequestAttack(player, 3).from(characters.which(OwnedBy(player)).which(Being(Shooter.class)).get());
        attack.allowCondition(false)
                .to(characters.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).get());
    }

    @Override
    public Card copy() {
        return new PreciseShot(player);
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String getName() {
        return "精準射擊";
    }

    @Override
    public CardParty getParty() {
        return CardParty.TRANSPARENT;
    }
}
