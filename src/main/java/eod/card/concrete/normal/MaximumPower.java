package eod.card.concrete.normal;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.exceptions.NotSupportedException;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Machine;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class MaximumPower extends NormalCard {
    public MaximumPower() {
        super(8);
    }
    @Override
    public void applyEffect() {
        WarObject[] machines = WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Machine.class)).get();

        for(WarObject object:machines) {
            Machine machine = (Machine) object;
            machine.addHealth(2);
            machine.addAttack(2);
        }

        for(WarObject object:machines) {
            Machine machine = (Machine) object;
            machine.attack();
        }
    }

    @Override
    public Card copy() {
        Card c = new MaximumPower();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "最大出力";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
