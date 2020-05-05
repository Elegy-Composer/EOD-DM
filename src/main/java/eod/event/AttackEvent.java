package eod.event;

import eod.characters.Character;
import eod.Player;

public abstract class AttackEvent {
    protected Player sender;
    protected Character attacker;
    protected int hp;
    protected boolean allowCondition;
    protected boolean willSuccess;
    public AttackEvent(Player sender, Character attacker, int hp, boolean allowCondition, boolean willSuccess) {
        this.sender = sender;
        this.attacker = attacker;
        this.hp = hp;
        this.allowCondition = allowCondition;
        this.willSuccess = willSuccess;
    }

    public Player getSender() {
        return sender;
    }

    public Character getAttacker() {
        return attacker;
    }

    public int getHp() {
        return hp;
    }

    public boolean isConditionAllowed() {
        return allowCondition;
    }

    public boolean willSuccess() {
        return willSuccess;
    }

}
