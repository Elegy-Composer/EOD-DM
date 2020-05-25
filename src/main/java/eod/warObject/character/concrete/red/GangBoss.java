package eod.warObject.character.concrete.red;

import eod.Party;
import eod.Player;
import eod.card.abstraction.summon.SummonCard;
import eod.card.concrete.summon.GangBossSummon;
import eod.exceptions.NotSupportedException;
import eod.effect.EffectExecutor;
import eod.param.PointParam;
import eod.warObject.character.abstraction.assaulter.Fighter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static eod.effect.EffectFunctions.RequestRegionalAttack;
import static eod.specifier.WarObjectSpecifier.WarObject;
import static eod.specifier.condition.Conditions.*;

public class GangBoss extends Fighter {
    public GangBoss(Player player) {
        super(player, 3, 2, Party.RED);
    }

    @Override
    public SummonCard getSummonCard() {
        SummonCard card = new GangBossSummon();
        card.setPlayer(player);
        return card;
    }

    @Override
    public void updatePosition(Point point) {
        if(position == null) {
            // null means this boss doesn't have previous position.
            // indicating that it is a newly summoned GangBoss
            specialEffect();
        }
        super.updatePosition(point);
    }

    private void specialEffect() {
        Arrays.stream(
            WarObject(player.getBoard())
                .which(OwnedBy(player))
                .which(Being(Gangster.class)).get()
        ).forEach(object -> {
            Gangster gangster = (Gangster) object;
            gangster.addHealth(1);
            gangster.addAttack(1);
        });

    }


    @Override
    public String getName() {
        return "武鬥派頭頭";
    }

    @Override
    public void attack(EffectExecutor executor) {
        super.attack(executor);
        Arrays.stream(
            WarObject(player.getBoard())
                .which(InRangeOf(this))
                .which(Being(Gangster.class)).get()
        ).forEach(object -> {
            Gangster gangster = (Gangster) object;
            gangster.attack(executor);
        });

        executor.tryToExecute(
                RequestRegionalAttack(attack).from(this).to(getAttackRange())
        );

        afterAttack();
    }

    @Override
    public ArrayList<Point> getAttackRange() {
        ArrayList<Point> targets = new ArrayList<>();
        PointParam param = new PointParam();
        param.range = 1;
        targets.addAll(player.getFL(position, param));
        targets.addAll(player.getFront(position, param));
        targets.addAll(player.getFR(position, param));
        return targets;

    }
}
