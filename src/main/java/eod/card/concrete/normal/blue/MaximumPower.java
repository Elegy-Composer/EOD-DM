package eod.card.concrete.normal.blue;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Machine;

import static eod.effect.EffectFunctions.IncreaseAttack;
import static eod.effect.EffectFunctions.IncreaseHealth;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class MaximumPower extends NormalCard {
    public MaximumPower() {
        super(8);
    }
    @Override
    public void applyEffect(EffectExecutor executor) {
        WarObject[] machines = WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Machine.class)).get();

        for(WarObject object:machines) {
            Machine machine = (Machine) object;
            Effect[] effects = new Effect[]{
                IncreaseHealth(2).to(machine),
                IncreaseAttack(2).to(machine)
            };
            executor.tryToExecuteInSequence(effects);
        }

        for(WarObject object:machines) {
            Machine machine = (Machine) object;
            machine.attack(executor);
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
