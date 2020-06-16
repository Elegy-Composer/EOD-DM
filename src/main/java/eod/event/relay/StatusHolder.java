package eod.event.relay;

import eod.GameObject;
import eod.effect.Effect;
import eod.event.Event;
import eod.warObject.Status;
import eod.warObject.WarObject;

import java.util.ArrayList;

import static eod.effect.EffectFunctions.GiveStatus;

public abstract class StatusHolder implements GameObject {
    // holds status for a limited time
    // It will only remove the status if no other holder is holding its statuses.
    private WarObject carrier;

    public StatusHolder(WarObject carrier) {
        this.carrier = carrier;
        holdingStatus().forEach(status ->
                carrier.getPlayer().tryToExecute(
                        GiveStatus(status, Effect.HandlerType.Owner).to(carrier)
                ));
    }

    public WarObject getCarrier() {
        return carrier;
    }

    public abstract ArrayList<Status> holdingStatus();

    @Override
    public void teardown() {
        carrier.unregisterStatusHolder(this);
        for(Status status:holdingStatus()) {
            if(carrier.getStatusHolders().stream().filter(holder -> holder.holdingStatus().contains(status)).toArray().length == 0) {
                carrier.removeStatus(status);
            }
        }
        carrier = null;
    }
}
