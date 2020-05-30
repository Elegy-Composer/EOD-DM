package eod.warObject.character.concrete.transparent;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.AssassinSummon;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.transparent.AssassinsIntermediarySummon;
import eod.event.Event;
import eod.event.ObjectEnterEvent;
import eod.event.relay.EventReceiver;
import eod.param.PointParam;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

public class AssassinsIntermediary extends Character {
    public AssassinsIntermediary(Player player) {
        super(player, 2, 1, Party.TRANSPARENT);
        new OwnedAbilities();
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard c = new AssassinsIntermediarySummon();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "殺手掮客";
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().get4Ways(position, param);
    }

    private class OwnedAbilities implements EventReceiver {
        private ArrayList<Class<? extends Event>> canHandle;


        public OwnedAbilities() {
            canHandle = new ArrayList<>();
            canHandle.add(ObjectEnterEvent.class);
            AssassinsIntermediary.this.registerReceiver(this);
        }

        @Override
        public ArrayList<Class<? extends Event>> supportedEventTypes() {
            return canHandle;
        }

        @Override
        public void onEventOccurred(GameObject sender, Event event) {
            if(event instanceof ObjectEnterEvent) {
                ObjectEnterEvent e = (ObjectEnterEvent) event;
                if (e.getObject() == AssassinsIntermediary.this) {
                    player.drawFromDeck(AssassinSummon.class, 1);
                    teardown();
                }
            }
        }

        @Override
        public void teardown() {
            AssassinsIntermediary.this.unregisterReceiver(this);
            canHandle.clear();
            canHandle = null;
        }
    }
}
