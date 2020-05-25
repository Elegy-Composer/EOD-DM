package eod.card.concrete.normal;

import eod.Gameboard;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.warObject.character.concrete.blue.SWAT;
import eod.warObject.character.concrete.blue.SecureGuardBot;
import eod.warObject.character.concrete.transparent.Drone;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class EmergencySupport extends NormalCard {
    public EmergencySupport() {
        super(7);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        // TODO: add 3 cost to the player
        boolean followingActions = player.getLeader().getHp() < 7;
        Gameboard board = player.getBoard();

        SWAT swat = new SWAT(player);
        Drone drone = new Drone(player);
        SecureGuardBot bot = new SecureGuardBot(player);

        Effect[] effects = new Effect[]{
            Summon(swat).onOnePointOf(player, baseOrConflictEmpty()),
            Summon(drone).onOnePointOf(player, baseOrConflictEmpty()),
            Summon(bot).onOnePointOf(player, baseOrConflictEmpty())
        };

        executor.tryToExecuteInSequence(effects);

        if(followingActions) {
            swat.attack(executor);
            drone.attack(executor);
            bot.attack(executor);
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
    public String getName() {
        return "緊急增援";
    }

    @Override
    public Party getParty() {
        return Party.BLUE;
    }
}
