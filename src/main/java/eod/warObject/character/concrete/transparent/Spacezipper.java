package eod.warObject.character.concrete.transparent;

import eod.Party;
import eod.Player;
import eod.warObject.character.abstraction.Character;

import static eod.effect.EffectFunctions.RequestDirectAttack;
import static eod.specifier.WarObjectSpecifier.Touchable;
import static eod.specifier.condition.Conditions.OwnedBy;

public class Spacezipper extends Dictator {
    public Spacezipper(Player player) {
        super(player, 20, -1, Party.TRANSPARENT);
    }

    @Override
    public void attack() {
        RequestDirectAttack(player, 2)
                .from(this)
                .to(Touchable(player.getBoard()).which(OwnedBy(player.rival())).get());
    }

    @Override
    public String getName() {
        return "空間之鏈 - 圭月";
    }

    // TODO: Leader's special abilities
}
