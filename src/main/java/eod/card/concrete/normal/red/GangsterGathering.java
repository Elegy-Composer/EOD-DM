package eod.card.concrete.normal.red;

import eod.Gameboard;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.EffectExecutor;
import eod.warObject.character.concrete.red.Gangster;

import java.awt.*;

import static eod.effect.EffectFunctions.Summon;

public class GangsterGathering extends NormalCard {
    public GangsterGathering() {
        super(7);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        Gameboard board = player.getBoard();
        int x = player.selectPosition(player.getSelfConflict()).x;
        for(int y = 0;y < Gameboard.boardSize;y++) {
            if(!board.hasObjectOn(x, y)) {
                executor.tryToExecute(
                        Summon(new Gangster(player)).on(new Point(x, y))
                );
            }
        }
    }

    @Override
    public Card copy() {
        Card c = new GangsterGathering();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "看我烙人來";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
