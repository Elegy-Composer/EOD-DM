package eod.card.concrete.normal;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.param.DamageParam;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import static eod.effect.EffectFunctions.Damage;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.*;

public class SingleContract extends NormalCard {
    public SingleContract() {
        super(2);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        WarObject[]  characters = WarObject(player.getBoard()).which(OwnedBy(player.rival())).which(Being(Character.class)).which(WithoutStatus(Status.SNEAK)).get();
        executor.tryToExecute(
                Damage(new DamageParam(4), Effect.HandlerType.Rival).onOneOf(player, characters)
        );

        player.getDeck().dropAll(this);
        player.getHand().dropAll(this);
    }

    @Override
    public Card copy() {
        Card c = new SingleContract();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "單次合約";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
