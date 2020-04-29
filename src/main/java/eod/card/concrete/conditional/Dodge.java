package eod.card.concrete.conditional;

import static eod.effect.EffectFunctions.RequestMove;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.Targeted;

import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.CardParty;
import eod.card.abstraction.action.ConditionalCard;
import eod.listener.AttackListener;

public class Dodge extends ConditionalCard implements AttackListener {
    public Dodge(Player p) {
        super(p, 1);
    }

    @Override
    public String getName() {
        return "迴避";
    }

    @Override
    public CardParty getParty() {
        return CardParty.TRANSPARENT;
    }

    @Override
    public Card copy() {
        return new Dodge(player);
    }

    @Override
    public void effect() {
        RequestMove(player, 1).from(Character(player.getBoard()).which(Targeted()).get());
    }
}
