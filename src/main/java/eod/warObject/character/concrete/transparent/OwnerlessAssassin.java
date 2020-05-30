package eod.warObject.character.concrete.transparent;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.transparent.OwnerlessAssassinSummon;
import eod.effect.EffectExecutor;
import eod.event.Event;
import eod.event.RoundStartEvent;
import eod.event.relay.EventReceiver;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.character.abstraction.assaulter.Assassin;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class OwnerlessAssassin extends Assassin {
    public OwnerlessAssassin(Player player) {
        super(player, 2, 4, Party.TRANSPARENT);
        new OwnedAbilities();
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new OwnerlessAssassinSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "無主的刺客";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        executor.tryToExecute(
            RequestRegionalAttack(attack).from(this).to(player, getAttackRange(), 1)
        );
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }

    private class OwnedAbilities implements EventReceiver {

        public OwnedAbilities() {
            OwnerlessAssassin.this.registerReceiver(RoundStartEvent.class, this);
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(OwnerlessAssassin.this.hasStatus(Status.NO_EFFECT)) {
                return;
            }
            if(event instanceof RoundStartEvent) {
                RoundStartEvent e = (RoundStartEvent) event;
                OwnerlessAssassin.this.player = e.getStartedRound().getPlayer();
            }
        }

        @Override
        public void teardown() {
            OwnerlessAssassin.this.unregisterReceiver(RoundStartEvent.class, this);
        }
    }
}
