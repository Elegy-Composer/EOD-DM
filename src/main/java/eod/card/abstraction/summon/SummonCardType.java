package eod.card.abstraction.summon;

public enum SummonCardType {
    NORMAL, // the deck shouldn't contain 4 or more of the same normal sumon card.
    TOKEN, // those which shouldn't be put into the deck before the game.
           // but if it is later put in the deck in game, it's okay.
           // since we never know what effect will Spacezipper make.
    SPECIAL // the deck shouldn't contain 2 or more of the same special card.
}
