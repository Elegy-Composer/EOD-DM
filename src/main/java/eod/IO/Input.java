package eod.IO;

import eod.card.abstraction.Card;
import eod.warObject.WarObject;

import java.awt.*;

public interface Input {

    //This method will send requesting message to user, waiting for its name
    String waitForPlayerName();

    //This method will send requesting message to user, waiting for its response
    //and return the desired card.
    //Return null if player want to ended "Action Phase".
    Card waitForPlayCard(Card[] currentHand);

    //Thie method will send requesting message to user, waiting for its response
    //This will return the thing of user's choice.
    Point waitForChoosePointFrom(Point[] points);
    Card waitForChooseCardFrom(Card[] cards);
    WarObject waitForChooseWarObjectFrom(WarObject[] objects);
    WarObject[] waitForChooseMultipleWarObjectFrom(WarObject[] objects, int number);
}
