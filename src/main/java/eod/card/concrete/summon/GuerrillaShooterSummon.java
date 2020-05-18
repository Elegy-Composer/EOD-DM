package eod.card.concrete.summon;

import eod.Party;
import eod.Player;
import eod.card.abstraction.Card;
import eod.card.abstraction.summon.SummonCard;
import eod.card.abstraction.summon.SummonCardType;
import eod.warObject.character.concrete.red.GuerrillaShooter;

import java.awt.*;
import java.util.ArrayList;

import static eod.effect.EffectFunctions.Summon;

public class GuerrillaShooterSummon extends SummonCard {
    public GuerrillaShooterSummon(Player p) {
        super(p, SummonCardType.NORMAL);
    }

    @Override
    public void summon() {
        ArrayList<Point> canEnter = player.getBaseEmpty();
        canEnter.addAll(player.getConflictEmpty());
        Summon(player, new GuerrillaShooter(player)).from(canEnter);
    }

    @Override
    public Card copy() {
        return new GuerrillaShooterSummon(player);
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public String getName() {
        return "召喚 游擊隊槍手";
    }

    @Override
    public Party getParty() {
        return Party.RED;
    }
}
