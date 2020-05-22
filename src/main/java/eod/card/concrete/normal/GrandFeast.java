package eod.card.concrete.normal;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.event.AttackEvent;
import eod.event.Event;
import eod.event.RoundEndEvent;
import eod.warObject.Status;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.util.ArrayList;

import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.*;

public class GrandFeast extends NormalCard {

    ArrayList<Class<? extends Event>> canHandle;

    public GrandFeast() {
        super(3);
        canHandle = new ArrayList<>();
        canHandle.add(RoundEndEvent.class);
        canHandle.add(AttackEvent.class);
    }

    @Override
    public void applyEffect() {
        for(WarObject object:WarObject (player.getBoard()).which(OwnedBy(player)).which(Being(Character.class)).which(InParty(Party.RED)).get()) {
            object.addStatus(Status.FURIOUS);
        }
    }

    @Override
    public Card copy() {
        Card c = new GrandFeast();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "盛宴開始";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }

    @Override
    public void teardown() {
        super.teardown();
        canHandle.clear();
        canHandle = null;
    }
}
