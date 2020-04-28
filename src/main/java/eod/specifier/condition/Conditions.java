package eod.specifier.condition;

public class Conditions {
    public static AttackedCondition Injured() {
        return new AttackedCondition();
    }
    public static TargetedCondition Targeted() {
        return new TargetedCondition();
    }
}
