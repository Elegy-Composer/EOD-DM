package eod.warObject.leader.red;

import eod.GameObject;
import eod.Gameboard;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.concrete.attack.red.DeathPulse;
import eod.card.concrete.normal.EquivalentExchange;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.listener.EventListener;
import eod.param.AttackParam;
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

public class Sundar extends Leader {
    public Sundar(Player player) {
        super(player, 20);
        canHandle.add(ObjectDeadEvent.class);
    }

    @Override
    public String getName() {
        return "靈魂商人 - 桑德";
    }

    @Override
    public void attack() {
        Point p = player.selectPosition(player.getBoard().getSurroundingEmpty(position, 1));
        player.summonObject(new LittleGhost(player), p);
    }

    public void deathPulse() {
        ArrayList<Point> targets = player.getBoard().get8ways(position, Gameboard.boardSize);
        AttackParam param = new AttackParam();
        param.hp = 4;
        attack(targets, param);
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
    protected ArrayList<Card> generateSpecialOrder() {
        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new EquivalentExchange(player));
        deck.add(new DeathPulse(player));
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
            } else if(attacker instanceof Ghost && ((WarObject) attacker).getPlayer().equals(player)){
                player.getBoard().summonObject(new GhostOfHatred(player), new Point(x, y));
            }
        }
    }
}
