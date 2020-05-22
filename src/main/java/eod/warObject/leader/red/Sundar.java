package eod.warObject.leader.red;

import eod.GameObject;
import eod.Gameboard;
import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.concrete.command.DeathPulse;
import eod.card.concrete.command.EquivalentExchange;
import eod.effect.Attack;
import eod.effect.RegionalAttack;
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
        Point p = player.selectPosition(player.getBoard().getSurroundingEmpty(position, 1));
        player.summonObject(new LittleGhost(player), p);
    }

    public Attack deathPulse() {
        ArrayList<Point> targets = player.getBoard().get8ways(position, Gameboard.boardSize);
        return new RegionalAttack(4).from(this).to(targets);
    }

    @Override
    public ArrayList<Damageable> attack(Gameboard gameboard, ArrayList<Point> targets, AttackParam param) {
        ArrayList<Damageable> affected = new ArrayList<>();
        int hp = param.hp;
        this.damage(hp);
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
                player.tryToExecute(
                    Summon(new LittleGhost(player)).on(new Point(x, y))
                );
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
