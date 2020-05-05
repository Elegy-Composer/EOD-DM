package eod;

import eod.IO.Input;
import eod.IO.Output;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.ConditionalCard;
import eod.card.collection.Deck;
import eod.card.collection.Hand;
import eod.card.collection.SpecialDeck;
import eod.characters.Character;
import eod.event.AttackEvent;
import eod.event.listener.AttackListener;
import eod.snapshots.Snapshotted;

import java.awt.*;
import java.util.*;

public class Player implements Snapshotted, GameObject {

    private Deck deck;
    private Game game;
    private SpecialDeck specialDeck;
    private Hand hand;
    private Leader leader;
    private Input input;
    private Output output;
    private OnAttackListener listener = new OnAttackListener();

    public Player(Deck deck, Leader leader) {
        this(deck, leader, new Hand());
    }

    public Player(Deck deck, Leader leader, Hand hand) {
        this.deck = deck;
        this.specialDeck = SpecialDeck.generateSpecialDeck(deck);
        this.leader = leader;
        this.hand = hand;
    }

    public void attachToGame(Game game) {
        this.game = game;
        game.registerListener(listener);
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
        specialDeck.teardown();
        specialDeck = null;
        leader.teardown();
        leader = null;
        game.unregisterListener(listener);
        listener = null;
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
        Player clone = new Player(newDeck, leader, hand);
        clone.attachToGame(game);
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
        character.updatePosition(point);
    }

    public void sendAttackEvent(AttackEvent event) {
        if(event.isConditionAllowed()) {
            game.sendEvent(event);
        }
    }

    public void loseCharacter(Character character) {
        getBoard().removeCharacter(character);
    }


    class OnAttackListener implements AttackListener {

        @Override
        public void onAttack(Player sender, AttackEvent event) {
            if (sender.equals(rival())) {
                ConditionalCard[] candidates =
                    hand.stream()
                        .filter(card -> card instanceof ConditionalCard)
                        .filter(card ->
                            ((ConditionalCard)card).canHandle(event.getAttackType())
                        )
                        .toArray(ConditionalCard[]::new);
                ConditionalCard toUse = selectCard(candidates);
                hand.remove(toUse);
                if (event.willSuccess()) {
                    toUse.applyEffect();
                }
                toUse.teardown();
            }
        }
    }
}
