package eod.IO;

import eod.Player;
import eod.Round;
import eod.card.abstraction.Card;
import eod.effect.Effect;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Character;

import java.awt.*;

public interface Output {
    void sendPlayerOrder(Player player, boolean isFirst);
    void sendRoundStarted(Round round);
    void sendRoundStartEffectActivate();
    void sendDrawingCards(Player player);
    void sendReceivedCards(Player player, Card[] cards);
    void sendPlayerPlayedCard(Player player, Card card);
    void sendRoundEnded(Round round);

    void sendWarObjectHpChanged(WarObject object, int hp);
    void sendWarObjectApChanged(WarObject object, int ap);
    void sendWarObjectReceivedEffect(WarObject object, Effect effect);
    void sendCharacterAttacked(Character attacker, Character victim);
    void sendWarObjectMoved(WarObject object, Point destination);
    void sendWarObjectDied(WarObject object);
    void sendWarObjectActivatedEffect(WarObject object);
    void sendWarObjectSummoned(WarObject object, Point position);

    void sendWinning(Player player);
    void sendLosing(Player player);

}
