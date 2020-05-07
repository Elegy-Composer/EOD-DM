package eod.card.concrete.attack.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.AttackCard;
import eod.effect.RegionalAttack;
import eod.specifier.Accessing;
import eod.warObject.character.abstraction.assaulter.Shooter;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.Character;
import static eod.specifier.condition.Conditions.*;

public class PreciseShot extends AttackCard {
    public PreciseShot(Player p) {
        super(p);
    }

    @Override
    public void attack() {
        Accessing characters = Character(player.getBoard());
        RegionalAttack attack = RequestRegionalAttack(player, 3).from(characters.which(OwnedBy(player)).which(Being(Shooter.class)).get());
        attack.to(characters.which(OwnedBy(rival)).which(InRangeOf(attack.attacker())).get());
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
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
