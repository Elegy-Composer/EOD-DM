package eod.warObject.leader.red;

import eod.*;
import eod.card.abstraction.Card;
import eod.card.concrete.command.DeathPulse;
import eod.card.concrete.command.EquivalentExchange;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.listener.EventListener;
import eod.param.AttackParam;
import eod.param.PointParam;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.other.Ghost;
import eod.warObject.character.concrete.red.GhostOfHatred;
import eod.warObject.character.concrete.red.LittleGhost;
import eod.warObject.leader.Leader;
import eod.warObject.character.abstraction.Bunker;
import eod.warObject.character.abstraction.Machine;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class Sundar extends Leader implements EventListener {
    public Sundar(Player player) {
        super(player, 20, 0, Party.RED);
        canHandle.add(ObjectDeadEvent.class);
    }

    @Override
    public String getName() {
        return "靈魂商人 - 桑德";
    }

    @Override
    public void attack() {
        super.attack();
        PointParam param = new PointParam();
        param.emptySpace = true;
        param.range = 1;
        Point p = player.selectPosition(player.getBoard().getSurrounding(position, param));
        player.summonObject(new LittleGhost(player), p);
    }

    public void deathPulse() {
        PointParam pointParam = new PointParam();
        pointParam.range = Gameboard.boardSize;
        ArrayList<Point> targets = player.getBoard().get8ways(position, pointParam);
        AttackParam attackParam = new AttackParam();
        attackParam.hp = 4;
        attackParam.realDamage = true;
        attack(targets, attackParam);
    }

    @Override
    public ArrayList<Damageable> attack(ArrayList<Point> targets, AttackParam param) {
        ArrayList<Damageable> affected = new ArrayList<>();
        int hp = param.hp;
        this.damage(hp);
        Gameboard gameboard = player.getBoard();
        for(Point p:targets) {
            try {
                WarObject target = gameboard.getObjectOn(p.x, p.y);
                if(target instanceof Bunker || target instanceof Machine) {
                    continue;
                }
                if(target.getPlayer().equals(player)) {
                    if(target instanceof Ghost) {
                        ((Ghost) target).attack();
                    }
                } else {
                    ((Damageable) target).attacked(this, hp);
                    affected.add((Damageable) target);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return affected;
    }

    @Override
    protected ArrayList<Card> generateCommand() {
        ArrayList<Card> deck = new ArrayList<>();

        EquivalentExchange equivalentExchange = new EquivalentExchange();
        equivalentExchange.setPlayer(player);

        DeathPulse deathPulse = new DeathPulse();
        deathPulse.setPlayer(player);

        deck.add(equivalentExchange);
        deck.add(deathPulse);
        return deck;
    }

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
        if (event instanceof ObjectDeadEvent) {
            Damageable deadObject = ((ObjectDeadEvent) event).getDeadObject();
            WarObject object = (WarObject) deadObject;
            CanAttack attacker = deadObject.getAttacker();
            int x = object.position.x, y = object.position.y;
            if(deadObject instanceof Ghost && object.getPlayer().equals(player)) {
                heal(2);
            } else if (object.getPlayer().equals(player)) {
                Summon(player, new LittleGhost(player)).on(new Point(x, y));
            } else if(isDeadObjectOwnedByEnemy(object) && isGhostOrSundar(attacker) && ((WarObject) attacker).getPlayer().equals(player)){
                player.getBoard().summonObject(new GhostOfHatred(player), new Point(x, y));
            }
        }
    }

    private boolean isDeadObjectOwnedByEnemy(WarObject deadObject) {
        return deadObject.getPlayer().equals(player.rival());
    }

    private boolean isGhostOrSundar(CanAttack attacker) {
        return attacker instanceof Ghost || attacker instanceof Sundar;
    }
}
