package eod.card.concrete.command;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.CommandCard;
import eod.card.abstraction.action.AttackCard;
import eod.warObject.leader.red.Sundar;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;

public class DeathPulse extends AttackCard implements CommandCard {
    public DeathPulse(Player p) {
        super(p);
    }

    @Override
    public void attack() {
        Sundar sundar = (Sundar) WarObject(player.getBoard()).which(Being(Sundar.class)).get()[0];
        sundar.deathPulse();
    }

    @Override
    public Card copy() {
        return new DeathPulse(player);
    }

    @Override
    public int getCost() {
        return 4;
    }

    @Override
    public String getName() {
        return "死亡脈衝";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
