package eod.card.concrete.normal;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
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
    public DroneSupport(Player p) {
        super(p);
        board = p.getBoard();
    }

    @Override
    public void applyEffect() {
        Point firstDrone = Summon(player, new Drone(player)).from(board.allEmptySpaces(new Point(Gameboard.firstLine, 0)));
        if(twoSapper()) {
            ArrayList<Point> emptySpaces = board.getSurroundingEmpty(firstDrone, 1);
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
        return new DroneSupport(player);
    }

    @Override
    public int getCost() {
        return 3;
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
