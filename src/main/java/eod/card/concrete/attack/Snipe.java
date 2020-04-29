package eod.card.concrete.attack;

import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.CardParty;
import eod.card.abstraction.action.AttackCard;
import eod.characters.Sniper;
import eod.specifier.Accessing;
import eod.specifier.WarObjectSpecifier;

import static eod.effect.EffectFunctions.RequestAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.*;

public class Snipe extends AttackCard {
    public Snipe(Player p) {
        super(p, 2);
    }

    @Override
    public Card copy() {
        return new Snipe(player);
    }

    @Override
    public void attack() {
        Accessing characters = Character(player.getBoard());
        RequestAttack(player, 8)
                .from(characters.which(OwnedBy(player)).which(BelongsTo(Sniper.class)).get())
                .to(characters.which(OwnedBy(rival)).get());
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public CardParty getParty() {
        return null;
    }
}
