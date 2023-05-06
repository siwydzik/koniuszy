package pl.wad.game.wolfandsheeps.engine.positioncache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryPositionCache implements ModifiablePositionCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryPositionCache.class);
    protected Map<Long, Long> cache = new ConcurrentHashMap<>();
    protected BinaryStorage binaryStorage = new BinaryStorage();

    protected long binaryEntrySize = 0;

    public InMemoryPositionCache() {
    }

    public InMemoryPositionCache(long binaryEntrySize) {
        this.binaryEntrySize = binaryEntrySize;
    }

    @Override
    public Long get(long encodedBoard) {
        Long line = cache.get(encodedBoard);
        if (binaryEntrySize > 0 && line == null) {
            line = binaryStorage.find(encodedBoard);
        }
        return line;
    }

    @Override
    public void put(long encodedBoard, long encodedLine) {
        cache.put(encodedBoard, encodedLine);
        if (binaryEntrySize > 0 && cache.size() > binaryEntrySize) {
            moveToBinaryStorage();
        }
    }

    @Override
    public void put(long encodedBoard, long encodedLine, boolean persistable) {
        put(encodedBoard, encodedLine);
    }

    @Override
    public void finish() {

    }


    private synchronized void moveToBinaryStorage() {
        Collection<Long> keysToRemove = putToBinaryStorage();
        keysToRemove.stream().forEach(k -> cache.remove(k));
    }

    private Collection<Long> putToBinaryStorage() {
        if (cache.size() > binaryEntrySize) {
            LOGGER.info("putToBinaryStorage is about to begin, current size: " + binaryStorage.size());
            TreeMap<Long, Long> partMap = new TreeMap<>(Long::compare);
            partMap.putAll(cache);
            binaryStorage.putEntry(partMap);
            return partMap.keySet();
        }
        return Collections.EMPTY_LIST;
    }
}
