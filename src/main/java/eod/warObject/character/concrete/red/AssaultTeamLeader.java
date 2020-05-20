package eod.warObject.character.concrete.red;

import eod.GameObject;
import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.AssaultTeamLeaderSummon;
import eod.effect.Summon;
import eod.event.Event;
import eod.event.ObjectEnterEvent;
import eod.event.StatusAcquiredEvent;
import eod.warObject.Status;
import eod.warObject.character.abstraction.Character;

import java.util.ArrayList;

public class AssaultTeamLeader extends Character {
    public AssaultTeamLeader(Player player) {
        super(player, 1, 1, Party.RED);
        canHandle.add(ObjectEnterEvent.class);
        canHandle.add(StatusAcquiredEvent.class);
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

    @Override
    public void onEventOccurred(GameObject sender, Event event) {
        super.onEventOccurred(sender, event);
        if(event instanceof ObjectEnterEvent) {
            if(((ObjectEnterEvent) event).getObject() == this) {
                addAttack(1);
                addHealth(1);
            }
        }
        if(event instanceof StatusAcquiredEvent) {
            StatusAcquiredEvent e = (StatusAcquiredEvent) event;
            if(e.getObject() == this && e.getStatus() == Status.SNEAK) {
                addAttack(1);
                addHealth(1);
            }
        }
    }
}
