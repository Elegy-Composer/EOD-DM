package eod.card.concrete.normal;

import eod.Gameboard;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.param.PointParam;
import eod.specifier.Accessing;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class GroupFight extends NormalCard {
    public GroupFight() {
        super(3);
    }

    @Override
    public void applyEffect() {
        Gameboard board = player.getBoard();
        Accessing characters = WarObject(board).which(Being(Character.class));
        WarObject victim = player.selectObject(characters
            .which(OwnedBy(player.rival()))
            .which(WithoutStatus(Status.SNEAK)).get()
        );

        PointParam param = new PointParam();
        param.range = 1;
        board.getSurrounding(victim.position, param);
        int sumOfAttack = 0;
        for(WarObject object:characters.which(OwnedBy(player)).which(InPoints(board.getSurrounding(victim.position, param))).get()) {
            sumOfAttack += ((Character) object).getAttack();
        }

        RequestRegionalAttack(player, sumOfAttack).to(victim);
    }

    @Override
    public Card copy() {
        Card c = new GroupFight();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "群毆";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
