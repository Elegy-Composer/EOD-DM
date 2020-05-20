package eod.card.concrete.attack.transparent;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.AttackCard;
import eod.effect.EffectExecutor;
import eod.effect.RegionalAttack;
import eod.specifier.Accessing;
import eod.warObject.character.abstraction.assaulter.Shooter;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.Character;
import static eod.specifier.condition.Conditions.*;

public class PreciseShot extends AttackCard {

    @Override
    public void attack(EffectExecutor executor) {
        Accessing characters = Character(player.getBoard());
        RegionalAttack attack = RequestRegionalAttack(player, 3)
                .from(
                    characters.which(OwnedBy(player)).which(Being(Shooter.class)).get()
                );
        attack.to(characters.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).get());
    }

    @Override
    public Card copy() {
        Card c = new PreciseShot();
        c.setPlayer(player);
        return c;
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
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
