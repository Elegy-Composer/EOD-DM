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
        String name = player.getName();
        if(isFirst) {
            System.out.println(name + "是先手");
        } else {
            System.out.println(name + "是後手");
        }
    }

    @Override
    public void sendRoundStarted(Round round) {
        System.out.println(round.getPlayer().getName() + "的第" + round.getNumber() + "回合開始");
    }

    @Override
    public void sendRoundStartEffectActivate() {
        System.out.println("自動攻擊效果開始");
    }

    @Override
    public void sendDrawingCards(Player player) {
        System.out.println(player.getName() + "抽卡了");
    }

    @Override
    public void sendReceivedCards(Player player, Card[] cards) {
        System.out.println(player.getName() + "抽到了");
        for(int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            System.out.println(i+1 + ". " + card.getStringDescription());
        }
    }

    @Override
    public void sendPlayerPlayedCard(Player player, Card card) {
        System.out.println(player.getName() + "出了" + card.getStringDescription());
    }

    @Override
    public void sendRoundEnded(Round round) {
        System.out.println(round.getPlayer().getName() + "的第" + round.getNumber() + "回合結束了");
    }

    @Override
    public void sendWarObjectHpChanged(WarObject object, int hp) {
        System.out.println(object.getName() + "的HP變成了" + hp);
    }

    @Override
    public void sendWarObjectApChanged(WarObject object, int ap) {
        System.out.println(object.getName() + "的HP變成了" + ap);
    }

    @Override
    public void sendWarObjectReceivedEffect(WarObject object, Effect effect) {
        System.out.println(object.getName() + "受到了" + effect.toString());
    }

    @Override
    public void sendCanAttackAttacked(CanAttack attacker, Damageable victim) {
        WarObject attackSender = (WarObject) attacker;
        WarObject attackReceiver = (WarObject) victim;
        System.out.println(attackSender.getName() + "攻擊了" + attackReceiver.getName());
    }

    @Override
    public void sendWarObjectMoved(WarObject object, Point destination) {
        System.out.println(object.getName() + "移動到" + destination.toString());
    }

    @Override
    public void sendWarObjectActivatedEffect(WarObject object) {
        System.out.println(object.getName() + "發動了效果");
    }

    @Override
    public void sendWarObjectSummoned(WarObject object, Point position) {
        System.out.println(object.getName() + "被召喚到了" + position.toString() + "的位置");
    }
}
