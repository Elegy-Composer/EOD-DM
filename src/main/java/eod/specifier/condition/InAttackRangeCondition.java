package eod.specifier.condition;

import eod.Character;

import java.util.Arrays;

public class InAttackRangeCondition implements Condition{
    Character center;
    public InAttackRangeCondition(Character center) {
        this.center = center;
    }

    @Override
    public Character[] filter(Character[] characters) {
        return Arrays.stream(characters)
                .filter(this::inRange)
                .toArray(Character[]::new);
    }

    private boolean inRange(Character target) {
        int dx = Math.abs(target.position.x - center.position.x);
        int dy = Math.abs(target.position.y - center.position.y);

        return dx <= center.attackRange && dy <= center.attackRange;
    }
}
