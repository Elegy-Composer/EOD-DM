package eod.card.concrete.normal;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.concrete.red.Gangster;

import static eod.effect.EffectFunctions.*;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class TyrantsCommand extends NormalCard {
    public TyrantsCommand() {
        super(4);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        WarObject[] gangsters = WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Gangster.class)).get();

        for(WarObject gangster:gangsters) {
            executor.tryToExecute(
                IncreaseAttack(1).to((Gangster) gangster)
            );
            executor.tryToExecute(
                IncreaseHealth(1).to((Gangster) gangster)
            );
        }

        executor.tryToExecute(
                GiveStatus(Status.FURIOUS, Effect.HandlerType.Owner).to(gangsters)
        );

        for(WarObject gangster:gangsters) {
            ((Gangster) gangster).attack(executor);
        }
    }

    @Override
    public Card copy() {
        Card c = new TyrantsCommand();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "暴君的指揮";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
