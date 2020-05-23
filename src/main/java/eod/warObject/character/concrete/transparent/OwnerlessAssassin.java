package eod.warObject.character.concrete.transparent;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.OwnerlessAssassinSummon;
import eod.event.Event;
import eod.event.RoundStartEvent;
import eod.param.PointParam;
import eod.warObject.Status;
import eod.warObject.character.abstraction.assaulter.Assassin;

import java.awt.*;
import java.util.ArrayList;

public class OwnerlessAssassin extends Assassin {
    public OwnerlessAssassin(Player player) {
        super(player, 2, 4, Party.TRANSPARENT);
        canHandle.add(RoundStartEvent.class);
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
    public ArrayList<Point> getAttackRange() {
        PointParam param = new PointParam();
        param.range = 1;
        return player.getBoard().getSurrounding(position, param);
    }

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
        super.onEventOccurred(sender, event);
        if(hasStatus(Status.NO_EFFECT)) {
            return;
        }
        if(event instanceof RoundStartEvent) {
            RoundStartEvent e = (RoundStartEvent) event;
            player = e.getStartedRound().getPlayer();
        }
    }
}
