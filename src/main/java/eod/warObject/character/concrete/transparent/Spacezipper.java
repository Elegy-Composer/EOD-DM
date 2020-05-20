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

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class Spacezipper extends Character implements Marker {
    private ArrayList<Point> marked;
    public Spacezipper(Player player) {
        super(player, 5, 3, Party.TRANSPARENT);
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

        RequestRegionalAttack(attack).from(this).realDamage().to(targets);
    }

    @Override
    protected void move() {
        ArrayList<Point> possibleMoves = player.getBoard().allEmptySpaces();
        player.moveObject(this, player.selectPosition(possibleMoves));
    }

    @Override
    public String getName() {
        return "圭月";
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
        SummonCard c = new SpacezipperSummon();
        c.setPlayer(player);
        return c;
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
