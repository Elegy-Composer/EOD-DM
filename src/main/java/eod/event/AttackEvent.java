package eod.event;

import eod.Character;
import eod.Player;

public class AttackEvent {
    private Player sender;
    private Character attacker;
    private Character[] targets;
    private int hp;
    private boolean allowCondition;
    private boolean willSuccess;

    public AttackEvent(Player sender, Character attacker, Character[] targets, int hp,
                       boolean allowCondition, boolean willSuccess) {
        this.sender = sender;
        this.attacker = attacker;
        this.targets = targets;
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

    public Character[] getTargets() {
        return targets;
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
