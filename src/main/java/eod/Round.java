package eod;

import java.util.Objects;

public class Round implements GameObject {
    private Player roundPlayer;
    private int roundNumber;

    public Round(Player player, int roundNumber) {
        this.roundPlayer = player;
        this.roundNumber = roundNumber;
    }

    public Player getPlayer() {
        return roundPlayer;
    }

    public int getNumber() {
        return roundNumber;
    }

    @Override
    public void teardown() {
        roundPlayer = null;
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
