package eod;

import eod.IO.Input;
import eod.IO.Output;
import eod.card.abstraction.Card;
import eod.card.collection.Deck;
import eod.card.collection.Hand;
import eod.characters.Character;
import eod.snapshots.Snapshotted;

import java.awt.*;
import java.util.*;

public class Player implements Snapshotted<Player.Snapshot>,
                                GameObject {

    private Deck deck;
    private Game game;
    private Hand hand;
    private Leader leader;
    private Input input;
    private Output output;

    public Player(Deck deck, Leader leader) {
        this(deck, leader, new Hand());
    }

    public Player(Deck deck, Leader leader, Hand hand) {
        this.deck = deck;
        this.leader = leader;
        this.hand = hand;
    }

    public void attachToGame(Game game) {
        this.game = game;
    }

    public void handReceive(ArrayList<Card> h) {
        hand.receive(h);
    }

    public void drawFromDeck(int count) {
        Card[] cards = deck.draw(count);
        handReceive(new ArrayList<>(Arrays.asList(cards)));
    }

    public boolean checkInHand(Class<? extends Card> c) {
        return hand.containsType(c);
    }

    public void dropCards() {
        hand.dropAllCards();
    }

    public void dropCards(int k) throws IllegalArgumentException {
        hand.randomlyDropCards(k);
    }
    
    public void announceWon() {
        
    }
    
    public void announceLost() {
        
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
        hand.teardown();
        hand = null;
        deck.teardown();
        deck = null;
        leader.teardown();
        leader = null;
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
                Objects.equals(hand, player.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck, hand);
    }

    @Override
    public Snapshot takeSnapshot() {
        return new Snapshot();
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
        character.updatePosition(point);
    }

    public void loseCharacter(Character character) {
        getBoard().removeCharacter(character);
    }

    public class Snapshot implements eod.snapshots.Snapshot {
        private Deck.Snapshot deckSnapshot = deck.takeSnapshot();
        private Hand.Snapshot handSnapshot = hand.takeSnapshot();

        public Deck.Snapshot getDeck() {
            return deckSnapshot;
        }

        public Hand.Snapshot getHand() {
            return handSnapshot;
        }
    }
}
