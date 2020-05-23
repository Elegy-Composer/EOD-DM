package eod.warObject.character.concrete.transparent;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.OwnerlessAssassinSummon;
import eod.event.Event;
import eod.event.RoundStartEvent;
import eod.event.relay.EventReceiver;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.character.abstraction.assaulter.Assassin;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class OwnerlessAssassin extends Assassin {
    public OwnerlessAssassin(Player player) {
        super(player, 2, 4, Party.TRANSPARENT);
        new OwnedAbilities(this);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new OwnerlessAssassinSummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "無主的殺手";
    }

    @Override
    public void attack() {
        super.attack();
        RequestRegionalAttack(player, attack).from(this).to(getAttackRange(), 1);
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }

    private class OwnedAbilities implements EventReceiver {
        private OwnerlessAssassin holder;
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities(OwnerlessAssassin holder) {
            this.holder = holder;
            canHandle = new ArrayList<>();
            canHandle.add(RoundStartEvent.class);
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
                holder.player = e.getStartedRound().getPlayer();
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
