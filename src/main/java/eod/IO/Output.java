package eod.IO;

import eod.Player;
import eod.Round;
import eod.card.abstraction.Card;
import eod.effect.Effect;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

import java.awt.*;

public interface Output {
    public void sendPlayerOrder(Player player, boolean isFirst);
    public void sendRoundStarted(Round round);
    public void sendRoundStartEffectActivate();
    public void sendDrawingCards(Player player);
    public void sendReceivedCards(Player player, Card[] cards);
    public void sendPlayerPlayedCard(Player player, Card card);
    public void sendRoundEnded(Round round);

    public void sendWarObjectHpChanged(WarObject object, int hp);
    public void sendWarObjectApChanged(WarObject object, int ap);
    public void sendWarObjectReceivedEffect(WarObject object, Effect effect);
    public void sendCanAttackAttacked(CanAttack attacker, Damageable victim);
    public void sendWarObjectMoved(WarObject object, Point destination);
    public void sendWarObjectActivatedEffect(WarObject object);
    public void sendWarObjectSummoned(WarObject object, Point position);

}
