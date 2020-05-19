package eod.IO;

import eod.card.abstraction.Card;
import eod.warObject.WarObject;

import java.awt.*;

public interface Input {

    //This method will send requesting message to user, waiting for its name
    public String waitForPlayerName();

    //This method will send requesting message to user, waiting for its response
    //and return the desired card.
    //Return null if player want to ended "Action Phase".
    public Card waitForPlayCard(Card[] currentHand);

    //Thie method will send requesting message to user, waiting for its response
    //This will return the thing of user's choice.
    public Point waitForChoosePointFrom(Point[] points);
    public Card waitForChooseCardFrom(Card[] cards);
    public WarObject waitForChooseWarObjectFrom(WarObject[] objects);
    public WarObject[] waitForChooseMultipleWarObjectFrom(WarObject[] objects, int number);
}
