package eod.warObject.leader.red;

import eod.*;
import eod.card.abstraction.Card;
import eod.card.concrete.command.DeathPulse;
import eod.card.concrete.command.EquivalentExchange;
import eod.event.Event;
import eod.event.ObjectDeadEvent;
import eod.event.relay.EventReceiver;
import eod.param.AttackParam;
import eod.param.DamageParam;
import eod.param.PointParam;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.Status;
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

        afterAttack();
    }

    @Override
    public ArrayList<Damageable> attack(ArrayList<Point> targets, AttackParam param) {
        ArrayList<Damageable> affected = new ArrayList<>();
        int a;
        if(hasStatus(Status.FURIOUS)) {
            a = param.hp * 2;
        } else {
            a = param.hp;
        }
        this.damage(new DamageParam(param.hp));
        DamageParam dp = new DamageParam(a);
        dp.realDamage = param.realDamage;
        Gameboard gameboard = player.getBoard();
        for(Point p:targets) {
            try {
                WarObject target = gameboard.getObjectOn(p.x, p.y);
                if(target instanceof Bunker || target instanceof Machine) {
                    continue;
                }
                if(target.getPlayer().isPlayerA() == player.isPlayerA()) {
                    if(target instanceof Ghost) {
                        ((Ghost) target).attack();
                    }
                } else {
                    ((Damageable) target).attacked(this, dp);
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

    private class OwnedAbilities implements EventReceiver {
        private Sundar holder;
        private ArrayList<Class<? extends Event>> canHandle;


        public OwnedAbilities(Sundar holder) {
            this.holder = holder;
            this.canHandle = new ArrayList<>();
            canHandle.add(ObjectDeadEvent.class);
            holder.registerReceiver(this);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(hasStatus(Status.NO_EFFECT)) {
                return;
            }
            if (event instanceof ObjectDeadEvent) {
                Damageable deadObject = ((ObjectDeadEvent) event).getDeadObject();
                WarObject object = (WarObject) deadObject;
                CanAttack attacker = deadObject.getAttacker();
                int x = object.position.x, y = object.position.y;
                if(deadObject instanceof Ghost && object.getPlayer().equals(player)) {
                    heal(2);
                } else if (object.getPlayer().equals(player)) {
                    Summon(player, new LittleGhost(player)).on(new Point(x, y));
                } else if(object.getPlayer().isPlayerA() == holder.player.isPlayerA() && isGhostOrSundar(attacker) && ((WarObject) attacker).getPlayer().equals(player)){
                    player.getBoard().summonObject(new GhostOfHatred(player), new Point(x, y));
                }
            }
        }

        @Override
        public void teardown() {
            holder.unregisterReceiver(this);
            holder = null;
            canHandle.clear();
            canHandle = null;
        }
    }

    private boolean isGhostOrSundar(CanAttack attacker) {
        return attacker instanceof Ghost || attacker instanceof Sundar;
    }
}
