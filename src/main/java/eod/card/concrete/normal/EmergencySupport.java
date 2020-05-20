package eod.card.concrete.normal;

import eod.Gameboard;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.warObject.character.concrete.blue.SWAT;
import eod.warObject.character.concrete.blue.SecureGuardBot;
import eod.warObject.character.concrete.transparent.Drone;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class EmergencySupport extends NormalCard {

    @Override
    public void applyEffect() {
        // TODO: add 3 cost to the player
        boolean followingActions = player.getLeader().getHp() < 7;
        Gameboard board = player.getBoard();

        SWAT swat = new SWAT(player);
        Drone drone = new Drone(player);
        SecureGuardBot bot = new SecureGuardBot(player);

        Summon(player, swat).onOnePointOf(baseOrConflictEmpty());
        Summon(player, drone).onOnePointOf(baseOrConflictEmpty());
        Summon(player, bot).onOnePointOf(baseOrConflictEmpty());

        if(followingActions) {
            swat.attack();
            drone.attack();
            bot.attack();
        }
    }

    private ArrayList<Point> baseOrConflictEmpty() {
        ArrayList<Point> points = player.getBaseEmpty();
        points.addAll(player.getConflictEmpty());
        return points;
    }

    @Override
    public Card copy() {
        Card c = new EmergencySupport();
        c.setPlayer(player);
        return c;
    }

    @Override
    public int getCost() {
        return 7;
    }

    @Override
    public String getName() {
        return "緊急增援";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
