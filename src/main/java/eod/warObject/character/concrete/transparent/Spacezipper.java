package eod.warObject.character.concrete.transparent;

import eod.GameObject;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.transparent.SpacezipperSummon;
import eod.effect.EffectExecutor;
import eod.event.Event;
import eod.event.RoundStartEvent;
import eod.event.relay.EventReceiver;
import eod.param.PointParam;
import eod.warObject.Marker;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class Spacezipper extends Character implements Marker {
    private ArrayList<Point> marked;
    public Spacezipper(Player player) {
        super(player, 5, 3, Party.TRANSPARENT);
        marked = new ArrayList<>();
        registerReceiver(new OwnedAbilities());
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        ArrayList<Point> targets = new ArrayList<>();
        Gameboard board = player.getBoard();
        PointParam param = new PointParam();
        param.range = 1;
        for(Point p:getMarks()) {
            for(Point q:board.getSurrounding(p, param)) {
                if(!targets.contains(q)) {
                    targets.add(q);
                }
            }
            if(!targets.contains(p)) {
                targets.add(p);
            }
        }
        for(Point q:board.getSurrounding(position, param)) {
            if(!targets.contains(q)) {
                targets.add(q);
            }
        }
        executor.tryToExecute(
            RequestRegionalAttack(attack).from(this).realDamage().to(targets)
        );

    }

    @Override
    protected void move() {
        PointParam param = new PointParam();
        param.emptySpace = true;
        ArrayList<Point> possibleMoves = player.getBoard().allSpaces(new Point(-1,-1), param);
        player.moveObject(this, player.selectPosition(possibleMoves));
    }

    @Override
    public String getName() {
        return "圭月";
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

    private class OwnedAbilities implements EventReceiver {
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities() {
            canHandle = new ArrayList<>();
            canHandle.add(RoundStartEvent.class);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof RoundStartEvent) {
                RoundStartEvent e = (RoundStartEvent) event;
                if(e.getStartedRound().getPlayer().isPlayerA() == Spacezipper.this.player.isPlayerA()) {
                    Spacezipper.this.mark(Spacezipper.this.position);
                    Spacezipper.this.move();
                }
            }
        }

        @Override
        public void teardown() {
            Spacezipper.this.unregisterReceiver(this);
            canHandle.clear();
            canHandle = null;
            clearMark();
        }
    }
}
