package eod;

import java.util.Objects;

public class Round {
    private Player roundPlayer;
    private int roundNumber;

    public Round(Player player, int roundNumber) {
        this.roundPlayer = player;
        this.roundNumber = roundNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Round round = (Round) o;
        return roundNumber == round.roundNumber &&
                Objects.equals(roundPlayer, round.roundPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundPlayer, roundNumber);
    }
}
