package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.ToughGuySummon;
import eod.event.AfterObjectDamageEvent;
import eod.event.Event;
import eod.event.relay.EventReceiver;
import eod.param.DamageParam;
import eod.param.PointParam;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.RequestRegionalAttack;

public class ToughGuy extends Fighter {
    public ToughGuy(Player player) {
        super(player, 6, 3, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new ToughGuySummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "蠻勇的巨漢";
    }

    @Override
    public void attack() {
        super.attack();
        RequestRegionalAttack(player, attack).from(this).to(getAttackRange());

        afterAttack();
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }

    private class OwnedAbilities implements EventReceiver {
        private ToughGuy holder;
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities(ToughGuy holder) {
            this.holder = holder;
            canHandle = new ArrayList<>();
            canHandle.add(AfterObjectDamageEvent.class);
            holder.registerReceiver(this);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof AfterObjectDamageEvent) {
                AfterObjectDamageEvent e = (AfterObjectDamageEvent) event;
                if(e.getVictim() == holder) {
                    holder.addAttack(2);
                    holder.addHealth(2);
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
