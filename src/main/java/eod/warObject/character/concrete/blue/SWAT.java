package eod.warObject.character.concrete.blue;

import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.SWATSummon;
import eod.param.PointParam;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.WarObject;
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
        SummonCard c = new SWATSummon();
        c.setPlayer(player);
        return c;
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
        PointParam param = new PointParam();
        param.range = 1;
        ArrayList<Point> surrounding = gameboard.get4Ways(position, param);
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
        PointParam param = new PointParam();
        param.range = 1;
        r.addAll(player.getFL(position, param));
        r.addAll(player.getFR(position, param));
        r.addAll(player.getFront(position, param));
        Gameboard board = player.getBoard();
        for(Point point:r) {
            try {
                WarObject object = board.getObjectOn(point.x, point.y);
                if(object.getPlayer().isPlayerA() != player.isPlayerA() && object.hasStatus(Status.SNEAK)) {
                    r.remove(point);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("No object on the point.\n Skipping.");
            }
        }
        return r;
    }
}
