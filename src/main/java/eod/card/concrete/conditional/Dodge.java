package eod.card.concrete.conditional;

import static eod.effect.EffectFunctions.RequestMove;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.Targeted;

import eod.Player;
import eod.card.abstraction.Card;
import eod.Party;
import eod.card.abstraction.action.ConditionalCard;
import eod.card.abstraction.handler.AttackHandler;

public class Dodge extends ConditionalCard implements AttackHandler {
    public Dodge(Player p) {
        super(p, 1);
    }

    @Override
    public String getName() {
        return "迴避";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }

    @Override
    public Card copy() {
        return new Dodge(player);
    }

    @Override
    public void applyEffect() {
        RequestMove(player, 1).from(Character(player.getBoard()).which(Targeted()).get());
        // TODO: fix the targeted issues
    }
}
