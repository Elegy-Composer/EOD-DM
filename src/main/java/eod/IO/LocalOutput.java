package eod.IO;

import eod.Player;
import eod.Round;
import eod.card.abstraction.Card;
import eod.effect.Effect;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.WarObject;

import java.awt.*;

public class LocalOutput implements Output {

    @Override
    public void sendPlayerOrder(Player player, boolean isFirst) {
        if(isFirst) {
            System.out.println("您是先手");
        } else {
            System.out.println("您是後手");
        }
    }

    @Override
    public void sendRoundStarted(Round round) {
        System.out.println(round.getPlayer())
    }

    @Override
    public void sendRoundStartEffectAvtivate() {

    }

    @Override
    public void sendDrawingCards() {

    }

    @Override
    public void sendReceivedCards(Card[] cards) {

    }

    @Override
    public void sendPlayerPlayedCard(Player player, Card card) {

    }

    @Override
    public void sendRoundEnded() {

    }

    @Override
    public void sendWarObjectHpChanged(WarObject object, int hp) {

    }

    @Override
    public void sendWarObjectApChanged(WarObject object, int ap) {

    }

    @Override
    public void sendWarObjectReceivedEffect(WarObject object, Effect effect) {

    }

    @Override
    public void sendCanAttackAttacked(CanAttack attacker, Damageable victim) {

    }

    @Override
    public void sendWarObjectMoved(WarObject object, Point destination) {

    }

    @Override
    public void sendWarObjectActivatedEffect(WarObject object) {

    }

    @Override
    public void sendWarObjectSummoned(WarObject object, Point position) {

    }
}
