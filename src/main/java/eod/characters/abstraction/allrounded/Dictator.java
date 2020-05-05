package eod.characters.abstraction.allrounded;

import eod.Character;
import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;

import java.util.ArrayList;

public abstract class Dictator extends Character {
    public Dictator(Player player, int x, int y, int hp, int range, Party party) {
        super(player, x, y, hp, range, party);
    }
}
