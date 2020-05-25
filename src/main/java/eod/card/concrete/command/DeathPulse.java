package eod.card.concrete.command;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.AttackCard;
import eod.effect.Attack;
import eod.warObject.leader.red.Sundar;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class DeathPulse extends AttackCard {
    public DeathPulse() {
        super(4);
    }

    @Override
    public Attack attack() {
        Sundar sundar = (Sundar) WarObject(player.getBoard()).which(Being(Sundar.class)).which(OwnedBy(player)).get()[0];
        return sundar.deathPulse();
    }

    @Override
    public Card copy() {
        Card c = new DeathPulse();
        c.setPlayer(player);
        return c;
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
