package eod.card.concrete.command.sundar;

import eod.Gameboard;
import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.AttackCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.effect.RegionalAttack;
import eod.param.DamageParam;
import eod.param.PointParam;
import eod.warObject.CanAttack;
import eod.warObject.WarObject;
import eod.warObject.character.abstraction.Bunker;
import eod.warObject.character.abstraction.Machine;
import eod.warObject.character.abstraction.other.Ghost;
import eod.warObject.leader.red.Sundar;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Damage;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.Being;
import static eod.specifier.condition.Conditions.OwnedBy;

public class DeathPulse extends AttackCard {
    public DeathPulse() {
        super(4);
    }

    @Override
    public void attack(EffectExecutor executor) {
        Sundar sundar = (Sundar) WarObject(player.getBoard()).which(Being(Sundar.class)).which(OwnedBy(player)).get()[0];
        executor.tryToExecute(
                Damage(new DamageParam(4), Effect.HandlerType.Owner).on(sundar)
        );

        PointParam pointParam = new PointParam();
        pointParam.range = Gameboard.boardSize;
        ArrayList<Point> attackRange = player.getBoard().get8ways(sundar.position, pointParam);

        SpecialRegionalAttack SRA = new SpecialRegionalAttack(4);

        executor.tryToExecute(
                SRA.from(sundar).realDamage().to(attackRange, player.getBoard(), executor)
        );
    }

    @Override
    public Card copy() {
        Card c = new DeathPulse();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "死亡脈衝";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }

    private class SpecialRegionalAttack extends RegionalAttack {

        protected SpecialRegionalAttack(int hp) {
            super(hp);
        }

        @Override
        public SpecialRegionalAttack from(WarObject attacker) {
            this.attacker = (CanAttack) attacker;
            return this;
        }

        @Override
        public SpecialRegionalAttack realDamage() {
            param.realDamage = true;
            return this;
        }

        public RegionalAttack to(ArrayList<Point> candidates, Gameboard gameboard, EffectExecutor executor) {
            ArrayList<Point> targets = new ArrayList<>();
            for(Point p:candidates) {
                try {
                    WarObject object = gameboard.getObjectOn(p.x, p.y);
                    if(object instanceof Bunker || object instanceof Machine) {
                        continue;
                    }

                    if(object.getPlayer().isPlayerA() == player.isPlayerA() && object instanceof Ghost) {
                        Ghost ghost = (Ghost) object;
                        ghost.attack(executor);
                    } else {
                        targets.add(p);
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("There's no objects on the position. Skipping.");
                }
            }
            return to(targets);
        }
    }
}
