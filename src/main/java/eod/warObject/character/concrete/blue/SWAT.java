package eod.warObject.character.concrete.blue;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.SWATSummon;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class SWAT extends Character {
    public SWAT(Player player) {
        super(player, 3, 1, Party.BLUE);
    }

    @Override
    public SummonCard getSummonCard() {
        return new SWATSummon(player);
    }

    @Override
    public String getName() {
        return "特勤警員";
    }

    @Override
    public void attack() {
        decideAddHealthAndAttack();
        RequestRegionalAttack(player, attack).from(this).to(getAttackRange(), 1);
    }

    private void decideAddHealthAndAttack() {
        Gameboard gameboard = player.getBoard();
        ArrayList<Point> surrounding = gameboard.get4Ways(position, 1);
        Character c;
        for(Point p:surrounding) {
            try {
                c = gameboard.getObjectOn(p.x, p.y);
            } catch (IllegalArgumentException e) {
                continue;
            }
            if (c.getPlayer().isPlayerA() == player.isPlayerA()) {
                attack += 1;
                addHealth(1);
                break;
            }
        }
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        ArrayList<Point> r = new ArrayList<>();
        r.addAll(player.getFL(position, 1));
        r.addAll(player.getFR(position, 1));
        r.addAll(player.getFront(position, 1));
        return r;
    }
}
