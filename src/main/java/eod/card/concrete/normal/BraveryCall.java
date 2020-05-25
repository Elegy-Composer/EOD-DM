package eod.card.concrete.normal;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.specifier.Accessing;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestMove;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class BraveryCall extends NormalCard {
    public BraveryCall() {
        super(3);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        Accessing myCharacters = WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Character.class));
        WarObject toMove = player.selectObject(myCharacters.get());
        PointParam param = new PointParam();
        param.range = 1;
        param.emptySpace = true;
        Point currentPos = toMove.position;

        for(int i = 0;i < 3;i++) {
            ArrayList<Point> front = player.getFront(currentPos, param);
            if(front.size() == 0) {
                break;
            } else {
                currentPos = front.get(0);
            }
        }

        executor.tryToExecute(
                RequestMove().from(toMove).to(currentPos)
        );

        if(myCharacters.which(Being(Fighter.class)).get().length >= 2) {
            allFightersAttack(executor);
        }
    }

    private void allFightersAttack(EffectExecutor executor) {
        for(WarObject fighter:WarObject(player.getBoard()).which(OwnedBy(player)).which(Being(Fighter.class)).get()) {
            ((Fighter) fighter).attack(executor);
        }
    }

    @Override
    public Card copy() {
        Card c = new BraveryCall();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "勇氣號召";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
