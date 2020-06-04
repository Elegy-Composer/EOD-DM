package eod.event;

public interface Event {
    // TODO: remove DirectAttackEvent and RegionalAttackEvent.
    Class<? extends Event>[] allEvents = new Class[] {AfterObjectDamageEvent.class, AttackEvent.class,
            BeforeObjectDamageEvent.class, ObjectDeadEvent.class, ObjectEnterEvent.class,
            ObjectEnterEnemyBaseEvent.class, ObjectMovingEvent.class, RoundEndEvent.class,
            RoundStartEvent.class, StatusAcquiredEvent.class, TargetedEvent.class};
}
