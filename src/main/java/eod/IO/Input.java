package eod.IO;

import eod.card.abstraction.Card;

import java.awt.*;

public interface Input {

    //This method will send requesting message to user, waiting for its name
    public String waitForPlayerName();

    //This method will send requesting message to user, waiting for its response
    //and return the desired card.
    //Return null if player want to ended "Action stage(行動階段的英文不會)".
    public Card waitForPlayCard(Card[] currentHand);

    //Thie method will send requesting message to user, waiting for its response
    //This will return the point of user's choice.
    public Point waitForChoosePointFrom(Point[] points);
}
