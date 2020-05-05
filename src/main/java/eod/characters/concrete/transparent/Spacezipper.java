package eod.characters.concrete.transparent;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.concrete.conditional.PhaseTransfer;
import eod.characters.abstraction.allrounded.Dictator;

import java.util.ArrayList;

public class Spacezipper extends Dictator {
    public Spacezipper(Player player, int x, int y, int hp, int range) {
        super(player, x, y, hp, range, Party.TRANSPARENT);
    }
}
