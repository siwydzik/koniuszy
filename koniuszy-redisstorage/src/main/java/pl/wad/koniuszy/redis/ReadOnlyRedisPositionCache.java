package pl.wad.koniuszy.redis;

import pl.wad.game.wolfandsheeps.engine.positioncache.InMemoryPositionCache;

import java.util.Optional;

public class ReadOnlyRedisPositionCache extends InMemoryPositionCache {

    private LinesRedisStorage linesRedisStorage;
    private String tableName;

    public ReadOnlyRedisPositionCache(LinesRedisStorage linesRedisStorage, String tableName) {
        this.linesRedisStorage = linesRedisStorage;
        this.tableName = tableName;
    }

    @Override
    public Long get(long encodedBoard) {
        Long value = super.get(encodedBoard);
        if (value == null) {
            Optional<Long> move = linesRedisStorage.get(tableName, encodedBoard);
            if (move.isPresent()) {
                return move.get();
            }
        }
        return value;
    }

    @Override
    public void put(long encodedBoard, long encodedLine) {
        super.put(encodedBoard, encodedLine);
    }

    @Override
    public void finish() {
        super.finish();
    }

}
