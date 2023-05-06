package pl.wad.game.wolfandsheeps.engine.positioncache;

public interface ModifiablePositionCache extends PositionCache {
    void put(long encodedBoard, long encodedLine);

    void put(long encodedBoard, long encodedLine, boolean persistable);

    void finish();
}
