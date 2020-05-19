package eod.card.concrete.normal;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.param.PointParam;
import eod.warObject.character.abstraction.supporter.Sapper;
import eod.warObject.character.concrete.transparent.Drone;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class DroneSupport extends NormalCard {
    private Gameboard board;
    public DroneSupport() {
        super(3);
    }

    @Override
    public void setPlayer(Player p) {
        super.setPlayer(p);
        board = p.getBoard();
    }

    @Override
    public void applyEffect() {
        PointParam param = new PointParam();
        param.emptySpace = true;
        Point firstDrone = Summon(player, new Drone(player)).from(board.allSpaces(new Point(Gameboard.firstLine, 0), param));
        if(twoSapper()) {
            param.range = 1;
            ArrayList<Point> emptySpaces = board.getSurrounding(firstDrone, param);
            if(emptySpaces.size() != 0) {
                Summon(player, new Drone(player)).from(emptySpaces);
            }
        }
    }

    private boolean twoSapper() {
        return Character(player.getBoard())
                .which(OwnedBy(player))
                .which(Being(Sapper.class)).get()
                .length>=2;
    }

    @Override
    public Card copy() {
        Card c = new DroneSupport();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "無人機支援";
    }

    @Override
    public Party getParty() {
        return Party.TRANSPARENT;
    }
}
