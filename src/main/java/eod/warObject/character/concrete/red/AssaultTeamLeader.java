package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.AssaultTeamLeaderSummon;
import eod.event.*;
import eod.effect.EffectExecutor;
import eod.event.Event;
import eod.event.relay.EventReceiver;
import eod.event.ObjectEnterEvent;
import eod.event.RoundStartEvent;
import eod.event.StatusAcquiredEvent;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class AssaultTeamLeader extends Character {
    public AssaultTeamLeader(Player player) {
        super(player, 1, 1, Party.RED);
        new OwnedAbilities(this);
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        executor.tryToExecute(
                RequestRegionalAttack(attack).from(this).to(getAttackRange())
        );

        afterAttack();
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getFront(position, param);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new AssaultTeamLeaderSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "特攻隊隊長";
    }

    private class OwnedAbilities implements EventReceiver {
        private AssaultTeamLeader holder;
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities(AssaultTeamLeader holder) {
            this.holder = holder;
            canHandle = new ArrayList<>();
            canHandle.add(RoundStartEvent.class);
            canHandle.add(ObjectEnterEnemyBaseEvent.class);
            canHandle.add(StatusAcquiredEvent.class);
            holder.registerReceiver(this);
        }


        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof RoundStartEvent) {
                RoundStartEvent e = (RoundStartEvent) event;
                if (e.getStartedRound().getPlayer().isPlayerA() == holder.getPlayer().isPlayerA()) {
                    PointParam param = new PointParam();
                    param.emptySpace = true;
                    param.range = 1;
                    ArrayList<Point> front = player.getFront(position, param);
                    if(front.size() != 0) {
                        moveTo(front.get(0));
                    }
                }
            }
            if(event instanceof StatusAcquiredEvent) {
                StatusAcquiredEvent e = (StatusAcquiredEvent) event;
                if(e.getObject() == holder && e.getStatus() == Status.SNEAK) {
                    holder.addAttack(1);
                    holder.addHealth(1);
                }
            }
            if(event instanceof ObjectEnterEnemyBaseEvent) {
                ObjectEnterEnemyBaseEvent e = (ObjectEnterEnemyBaseEvent) event;
                if(e.getObject() == holder) {
                    holder.addAttack(1);
                    holder.addHealth(1);
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
}
