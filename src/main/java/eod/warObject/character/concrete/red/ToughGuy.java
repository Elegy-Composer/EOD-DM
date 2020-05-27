package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.ToughGuySummon;
import eod.effect.EffectExecutor;
import eod.event.AfterObjectDamageEvent;
import eod.event.Event;
import eod.event.relay.EventReceiver;
import eod.param.DamageParam;
import eod.param.PointParam;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.*;

public class ToughGuy extends Fighter {
    public ToughGuy(Player player) {
        super(player, 6, 3, Party.RED);
        new OwnedAbilities();
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
        return player.getBoard().getSurrounding(position, param);
    }

    private class OwnedAbilities implements EventReceiver {
        private ArrayList<Class<? extends Event>> canHandle;

        public OwnedAbilities() {
            canHandle = new ArrayList<>();
            canHandle.add(AfterObjectDamageEvent.class);
            ToughGuy.this.registerReceiver(this);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof AfterObjectDamageEvent) {
                AfterObjectDamageEvent e = (AfterObjectDamageEvent) event;
                if(e.getVictim() == ToughGuy.this) {
                    Player owner = ToughGuy.this.getPlayer();
                    owner.tryToExecute(
                            IncreaseAttack(2).to(ToughGuy.this)
                    );
                    owner.tryToExecute(
                            IncreaseHealth(2).to(ToughGuy.this)
                    );
                }
            }
        }

        @Override
        public void teardown() {
            ToughGuy.this.unregisterReceiver(this);
            canHandle.clear();
            canHandle = null;
        }
    }
}
