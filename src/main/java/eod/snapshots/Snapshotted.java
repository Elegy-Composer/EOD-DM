package eod.snapshots;

public interface Snapshotted<T extends Snapshot> {
    T takeSnapshot();
}
