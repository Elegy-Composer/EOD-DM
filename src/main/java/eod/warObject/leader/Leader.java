package eod.warObject.leader;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.warObject.CanAttack;
import eod.warObject.Damageable;
import eod.warObject.Status;
import eod.warObject.character.abstraction.Character;

import java.awt.*;
import java.util.ArrayList;

public abstract class Leader extends Character implements Damageable, CanAttack {
    private ArrayList<Card> command;

    public Leader(Player player, int hp, int attack, Party party) {
        super(player, hp, attack, party);
        max_hp = hp;
        this.hp = max_hp;

        command = generateCommand();
    }

    @Override
    public void heal(int hp) {
        if(this.hp + hp >= max_hp) {
            this.hp = max_hp;
        }
        else {
            this.hp += hp;
        }
    }

    @Override
    public void addHealth(int hp) {
        this. max_hp += max_hp;
        this.hp += hp;
    }

    @Override
    public void addAttack(int a) {
        attack += a;
    }

    public Leader enterArena() {
        ArrayList<Point> points = player.getBaseEmpty();
        position = player.selectPosition(points);
        player.summonObject(this, position);
        return this;
    }

    protected abstract ArrayList<Card> generateCommand();

    @Override
    public void attacked(CanAttack attacker, int hp) {
        this.attacker = attacker;
        damage(hp);
        this.attacker = null;
    }

    @Override
    public CanAttack getAttacker() {
        return attacker;
    }

    @Override
    public void damage(int hp) {
        this.hp -= hp;
        if(this.hp <= 0) {
            die();
        }
    }

    @Override
    public void die() {
        teardown();
        player.loseLeader();
    }

    public Card getCommand() {
        return command.remove(0);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public SummonCard getSummonCard() {
        // since the game ends if the leader leaves the board, there's no need to generate the summon card.
        return null;
    }

    @Override
    public void teardown() {
        player = null;
    }
}
