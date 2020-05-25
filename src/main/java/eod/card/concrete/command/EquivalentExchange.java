package eod.card.concrete.command;

import eod.Party;
import eod.card.abstraction.Card;
import eod.card.abstraction.action.NormalCard;
import eod.effect.Effect;
import eod.effect.EffectExecutor;
import eod.warObject.character.concrete.red.LittleGhost;
import eod.warObject.leader.Leader;

import static eod.effect.EffectFunctions.RequestDirectAttack;
import static eod.effect.EffectFunctions.Summon;

public class EquivalentExchange extends NormalCard {
    public EquivalentExchange() {
        super(1);
    }

    @Override
    public void applyEffect(EffectExecutor executor) {
        Leader leader = player.getLeader();
        Effect[] effects = new Effect[] {
            //An attack to itself, then the handler should be its owner
            RequestDirectAttack(2, Effect.HandlerType.Owner).from(leader).to(leader),
            Summon(new LittleGhost(player)).onOnePointOf(player, player.getBaseEmpty())
        };
        executor.tryToExecuteInSequence(effects);
    }

    @Override
    public Card copy() {
        Card c = new EquivalentExchange();
        c.setPlayer(player);
        return c;
    }

    @Override
    public String getName() {
        return "等價交換";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
