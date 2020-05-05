package eod.event;

import eod.Round;

public class RoundEndEvent implements Event {
    private Round endedRound;

    public RoundEndEvent(Round endedRound) {
        this.endedRound = endedRound;
    }

    public Round getEndedRound() {
        return endedRound;
    }
}
