package eod.IO;

import eod.card.abstraction.Card;

import java.awt.*;
import java.util.Scanner;

public class LocalInput implements Input {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public String waitForPlayerName() {
        System.out.println("請輸入您的名字");
        return scanner.nextLine();
    }

    @Override
    public Card waitForPlayCard(Card[] currentHand) {
        System.out.println("請出牌\n或-1結束行動階段");
        System.out.println("目前的手牌如下：");
        for(int i = 0; i < currentHand.length; i++) {
            Card card = currentHand[i];
            System.out.println(
                i+1 + ". " + card.getName() + "(" + card.getParty().toString() + ")"
            );
        }

        do {
            int response = scanner.nextInt();
            if (response == -1) {
                return null;
            }
            if (1 <= response && response <= currentHand.length) {
                return currentHand[response-1];
            }
        } while(true);
    }

    @Override
    public Point waitForChoosePointFrom(Point[] points) {
        System.out.println("請選擇一個位置：");
        for(int i = 0; i < points.length; i++) {
            Point point = points[i];
            System.out.println(
                    i+1 + ". " + "(" + point.x + "," + point.y + ")"
            );
        }

        do {
            int response = scanner.nextInt();
            if (1 <= response && response <= points.length) {
                return points[response-1];
            }
        } while(true);
    }
}
