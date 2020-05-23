package eod.card.concrete.attack.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.AttackCard;
import eod.effect.RegionalAttack;
import eod.specifier.Accessing;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;
import eod.warObject.character.abstraction.assaulter.Shooter;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class PreciseShot extends AttackCard {
    public PreciseShot() {
        super(3);
    }

    @Override
    public void attack() {
        Accessing characters = WarObject(player.getBoard()).which(Being(Character.class));
        RegionalAttack attack = RequestRegionalAttack(player, 3)
                .from(
                    characters.which(OwnedBy(player)).which(Being(Shooter.class)).get()
                );
        attack.realDamage().to(characters.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).which(WithoutStatus(Status.SNEAK)).get());
    }

    @Override
    public Card copy() {
        Card c = new PreciseShot();
        c.setPlayer(player);
        return c;
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
