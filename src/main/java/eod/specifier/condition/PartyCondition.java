package eod.specifier.condition;

import eod.Party;
import eod.warObject.WarObject;

import java.util.Arrays;

public class PartyCondition implements Condition {
    private Party party;
    public PartyCondition(Party party) {
        this.party = party;
    }

    @Override
    public WarObject[] filter(WarObject[] objects) {
        return Arrays.stream(objects)
                .filter(object -> object.getParty() == party)
                .toArray(WarObject[]::new);
    }
}
