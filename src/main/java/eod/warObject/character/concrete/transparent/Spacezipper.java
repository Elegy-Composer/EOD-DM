package eod.warObject.character.concrete.transparent;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.allrounded.Dictator;

import static eod.effect.EffectFunctions.RequestDirectAttack;
import static eod.specifier.WarObjectSpecifier.*;
import static eod.specifier.condition.Conditions.OwnedBy;

public class Spacezipper extends Dictator {
    public Spacezipper(Player player, int x, int y, int hp, int range) {
        super(player, x, y, hp, range, Party.TRANSPARENT);
    }

    @Override
    public void attack() {
        RequestDirectAttack(player, 2)
                .from(this)
                .to(Touchable(player.getBoard()).which(OwnedBy(player.rival())).get());
    }

    // TODO: Leader's special abilities
}
