package eod.event;

import eod.Player;
import eod.param.AttackParam;
import eod.warObject.character.abstraction.Character;

public abstract class AttackEvent implements Event {
    protected Player sender;
    protected Character attacker;
    public AttackParam param;

    public AttackEvent(Player sender, Character attacker, AttackParam param) {
        this.sender = sender;
        this.attacker = attacker;
        this.param = param;
    }

    public Player getSender() {
        return sender;
    }

    public Character getAttacker() {
        return attacker;
    }
}
