package eod.event;

import eod.Round;

public class RoundStartEvent implements Event {
    private Round startedRound;

    public RoundStartEvent(Round started) {
        this.startedRound = started;
    }

    public Round getStartedRound() {
        return startedRound;
    }
}
