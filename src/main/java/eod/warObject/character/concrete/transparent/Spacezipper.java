package eod.warObject.character.concrete.transparent;

import eod.GameObject;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.SpacezipperSummon;
import eod.event.Event;
import eod.event.RoundStartEvent;
import eod.warObject.Marker;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestDirectAttack;
import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.Touchable;
import static eod.specifier.condition.Conditions.OwnedBy;

public class Spacezipper extends Character implements Marker {
    private ArrayList<Point> marked;
    public Spacezipper(Player player) {
        super(player, 5, 3, -1, Party.TRANSPARENT);
        canHandle.add(RoundStartEvent.class);
        marked = new ArrayList<>();
    }

    @Override
    public void attack() {
        ArrayList<Point> targets = new ArrayList<>();
        Gameboard board = player.getBoard();

        for(Point p:getMarks()) {
            for(Point q:board.getSurrounding(p, 1)) {
                if(!targets.contains(q)) {
                    targets.add(q);
                }
            }
            if(!targets.contains(p)) {
                targets.add(p);
            }
        }
        for(Point q:board.getSurrounding(position, 1)) {
            if(!targets.contains(q)) {
                targets.add(q);
            }
        }

        RequestRegionalAttack(player, attack).from(this).realDamage().to(targets);
    }

    @Override
    protected void move() {
        ArrayList<Point> possibleMoves = player.getBoard().allEmptySpaces();
        player.moveObject(this, player.selectPosition(possibleMoves));
    }

    @Override
    public String getName() {
        return "空間之鏈 - 圭月";
    }

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
        super.onEventOccurred(sender, event);
        if(event instanceof RoundStartEvent) {
            mark(position);
            move();
        }
    }

    @Override
    public void teardown() {
        super.teardown();
        clearMark();
    }

    @Override
    public SummonCard getSummonCard() {
        return new SpacezipperSummon(player);
    }

    @Override
    public void mark(Point point) {
        if(!marked.contains(point)) {
            marked.add(point);
        }
    }

    @Override
    public void unmark(Point point) {
        marked.remove(point);
    }

    @Override
    public void clearMark() {
        marked.clear();
    }

    @Override
    public ArrayList<Point> getMarks() {
        return marked;
    }
}
