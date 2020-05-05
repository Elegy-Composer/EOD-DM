package eod.card.concrete.conditional;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.ConditionalCard;
import eod.characters.concrete.transparent.Spacezipper;
import eod.effect.Move;
import eod.characters.Character;

import java.util.ArrayList;
import java.util.Arrays;

import static eod.effect.EffectFunctions.RequestMove;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.Targeted;

public class PhaseTransfer extends ConditionalCard {
    public PhaseTransfer(Player p) {
        // TODO: specify the level of the card
        super(p, 1);
    }

    @Override
    public ArrayList<ConditionType> capableConditions() {
        ConditionType[] conditions = new ConditionType[] {
                ConditionType.ATTACK_DIRECT, ConditionType.ATTACK_REGIONAL
        };
        return new ArrayList<>(Arrays.asList(conditions));
    }

    @Override
    public void applyEffect() {
        Gameboard gameboard = player.getBoard();
        Move move = RequestMove(player).from(Character(player.getBoard()).which(Targeted()).get());
        Character target = move.getTarget();
        if(target.getClass() == Spacezipper.class) {
            move.to(gameboard.allEmptySpaces());
        } else {
            move.to(gameboard.allEmptySpaces(target.position));
        }
    }

    @Override
    public Card copy() {
        return new PhaseTransfer(player);
    }

    @Override
    public String getName() {
        return "相位轉移";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
