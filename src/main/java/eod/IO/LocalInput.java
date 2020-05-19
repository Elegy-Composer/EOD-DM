package eod.IO;

import eod.card.abstraction.Card;
import eod.warObject.WarObject;

import java.awt.*;
import java.util.Arrays;
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
                    i+1 + ". (" + point.x + "," + point.y + ")"
            );
        }

        do {
            int response = scanner.nextInt();
            if (1 <= response && response <= points.length) {
                return points[response-1];
            }
        } while(true);
    }

    @Override
    public Card waitForChooseCardFrom(Card[] cards) {
        System.out.println("請選擇一張卡：");
        for(int i = 0; i < cards.length; i++) {
            System.out.println(
                    i+1 + ". " + cards[i].getStringDescription()
            );
        }

        do {
            int response = scanner.nextInt();
            if (1 <= response && response <= cards.length) {
                return cards[response-1];
            }
        } while(true);
    }

    @Override
    public WarObject waitForChooseWarObjectFrom(WarObject[] objects) {
        return waitForChooseWarObjectFrom(objects, 1)[0];
    }

    @Override
    public WarObject[] waitForChooseWarObjectFrom(WarObject[] objects, int number) {
        System.out.println("請選擇" + number + "個場上物體：");
        showWarObjects(objects);

        int[] responses = new int[number];

        for(int i = 0; i < number; i++) {
            do {
                int response = scanner.nextInt();
                if (1 <= response && response <= objects.length) {
                    responses[i] = response;
                    break;
                }
            } while(true);
        }

        return Arrays.stream(responses)
                        .mapToObj((num) -> objects[num-1])
                        .toArray(WarObject[]::new);
    }

    private void showWarObjects(WarObject[] objects) {
        for(int i = 0; i < objects.length; i++) {
            WarObject card = objects[i];
            Point position = card.getPosition();
            System.out.println(
                    i+1 + ". " + card.getName() + "(" + position.x + ", " + position.y + ")"
            );
        }
    }
}
