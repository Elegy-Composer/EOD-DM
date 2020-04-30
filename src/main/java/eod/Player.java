package eod;

import eod.card.abstraction.Card;
import eod.card.abstraction.ICard;
import eod.card.abstraction.action.ConditionalCard;
import eod.effect.Target;
import eod.listener.AttackListener;
import eod.snapshots.Snapshotted;

import java.awt.*;
import java.util.*;

import static eod.effect.EffectFunctions.Target;

public class Player implements Snapshotted, GameObject {

    private Deck deck;
    private Game game;
    private SpecialDeck specialDeck;
    private ArrayList<Card> hand = new ArrayList<>();
    private Leader leader;

    public Player(Deck deck, Game game, Leader leader) {
        this.game = game;
        this.deck = deck;
        specialDeck = SpecialDeck.generateSpecialDeck(deck);
        this.leader = leader;
    }

    public void handReceive(ArrayList<Card> h) {
        hand.addAll(h);
    }

    public void drawFromDeck(int count) {
        deck.draw(count);
    }

    public boolean checkInHand(Class<? extends ICard> c) {
        for(Card card: hand) {
            if (card.cardTypeEquals(c)) {
                return true;
            }
        }
        return false;
    }

    public void dropCards() {
        hand.clear();
    }

    public void dropCards(int k) throws IllegalArgumentException {
        if(k<0) throw new IllegalArgumentException("Attempting to drop negative number of cards");
        if(k>=hand.size()) {
            dropCards();
            return;
        }

        Random random = new Random();
        for(int i = 0;i < k;i++) {
            int toDrop = random.nextInt(hand.size());
            hand.remove(toDrop);
        }
    }

    //TODO: implement validateDeck, we didn't do it know because the types of card aren't enough
    public boolean validateDeck() {
        return true;
    }

    public Gameboard getBoard() {
        return game.getBoard();
    }

    public boolean isLeaderAlive() {
        return leader.isAlive();
    }

    @Override
    public void teardown() {
        hand.clear();
        hand = null;
        deck.teardown();
        deck = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(deck, player.deck) &&
                Objects.equals(specialDeck, player.specialDeck) &&
                Objects.equals(hand, player.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck, specialDeck, hand);
    }

    @Override
    public Player snapshot() {
        Deck newDeck = deck.snapshot();
        Player clone = new Player(newDeck, game, leader);
        clone.handReceive(hand);

        return clone;
    }

    public Player rival() {
        return game.getRivalPlayer(this);
    }

    public Character selectCharacter(Character[] characters) {
        //TODO: asks the player to select a character
        Random random = new Random();
        return characters[random.nextInt(characters.length)];
    }

    public Point selectPosition(ArrayList<Point> points) {
        //TODO: connection with the frontend
        Random random = new Random();
        return points.get(random.nextInt(points.size()));
    }

    public <T extends Card> T selectCard(T[] cards) {
        //TODO:connection with the frontend
        Random random = new Random();
        return cards[random.nextInt(cards.length)];
    }

    public void moveCharacter(Character character, Point point) {
        game.getBoard().moveElement(character.position, point);
        character.moveTo(point);
    }

    public void attack(Character from, Character[] to, int hp) {
        attack(from, to, hp, true, true);
    }

    public void attack(Character from, Character[] to, int hp, boolean allowCondition, boolean willSuccess) {
        Target targets = Target().on(to);
        if(allowCondition) {
            game.triggerTargetedListener(this, willSuccess);
            to = Arrays.stream(to)
                    .filter(target -> target.isTargeted)
                    .toArray(Character[]::new);
        }
        for(Character target:to) {
            target.damage(hp);
        }
        targets.unTarget();
    }

    public void targetedTrigger() {
        // If the player's characters has been targeted, trigger this function
        targetedTrigger(true);
    }

    public boolean targetedTrigger(boolean willSuccess) {
        ConditionalCard[] candidates = hand.stream()
                .filter(card -> card instanceof ConditionalCard)
                .filter(card -> card instanceof AttackListener)
                .toArray(ConditionalCard[]::new);
        ConditionalCard toUse = selectCard(candidates);
        hand.remove(toUse);
        toUse.teardown();
        if(willSuccess) {
            toUse.effect();
        }
        return willSuccess;
    }

    public void loseCharacter(Character character) {
        Point pos = character.position;
        int x = pos.x, y = pos.y;
        getBoard().removeObject(x, y);
    }
}
