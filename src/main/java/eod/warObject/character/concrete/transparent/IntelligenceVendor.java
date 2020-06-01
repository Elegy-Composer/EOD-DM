package eod.warObject.character.concrete.transparent;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.transparent.IntelligenceVendorSummon;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.event.Event;
import eod.event.RoundEndEvent;
import eod.event.relay.EventReceiver;
import eod.event.relay.StatusHolder;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static eod.effect.EffectFunctions.GiveStatus;

public class IntelligenceVendor extends Character {
    public IntelligenceVendor(Player player) {
        super(player, 3, 0, Party.TRANSPARENT);
        registerReceiver(RoundEndEvent.class, new OwnedAbilities());
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new IntelligenceVendorSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "情報販";
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        return player.getBoard().allSpaces(new Point(-1, 0), new PointParam());
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        try {
            Point p = player.selectPosition(getAttackRange());
            WarObject object = player.getBoard().getObjectOn(p.x, p.y);

            object.registerReceiver(RoundEndEvent.class, new AttackEffectLock(object));
        } catch (IllegalArgumentException e) {
            System.out.println("There's no object on the selected point. Skipping.");
        }

        afterAttack();
    }


    private class AttackEffectLock implements StatusHolder {

        private WarObject holder;
        private ArrayList<Status> holdingStatus;

        public AttackEffectLock(WarObject object) {
            this.holder = object;

            holdingStatus = new ArrayList<>();
            holdingStatus.add(Status.NO_ATTACK);
            holdingStatus.add(Status.NO_EFFECT);

            holdingStatus.forEach(status -> holder.getPlayer().tryToExecute(
                    GiveStatus(status, Effect.HandlerType.Owner).to(object)
                    ));
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof RoundEndEvent) {
                RoundEndEvent e = (RoundEndEvent) event;
                if(e.getEndedRound().getPlayer().isPlayerA() != player.rival().isPlayerA()) {
                    // End of the enemy's round.
                    teardown();
                }
            }
        }

        @Override
        public void teardown() {
            holder.unregisterReceiver(RoundEndEvent.class, this);
            EventReceiver[] statusHolders = holder.getStatusHolders();
            holdingStatus.forEach(status -> {
                    if(Arrays.stream(statusHolders)
                            .filter(receiver -> (
                                    (StatusHolder) receiver).holdingStatus().contains(status)
                            ).toArray().length == 0) {
                        holder.removeStatus(status);
                    }
            });

            holder = null;
            holdingStatus.clear();
            holdingStatus = null;
        }

        @Override
        public ArrayList<Status> holdingStatus() {
            return holdingStatus;
        }
    }



    private class OwnedAbilities implements EventReceiver {

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(IntelligenceVendor.this.hasStatus(Status.NO_EFFECT)) {
                return;
            }
            if(event instanceof RoundEndEvent) {
                RoundEndEvent e = (RoundEndEvent) event;
                if(e.getEndedRound().getPlayer().isPlayerA() == player.isPlayerA()) {
                    if(player.getHand().size() <= 2) {
                        player.drawFromDeck(2);
                    } else {
                        player.drawFromDeck(1);
                    }
                }
            }
        }

        @Override
        public void teardown() {
            IntelligenceVendor.this.unregisterReceiver(RoundEndEvent.class, this);
        }
    }
}
