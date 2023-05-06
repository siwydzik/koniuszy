package pl.wad.koniuszy.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wad.game.wolfandsheeps.engine.positioncache.InMemoryPositionCache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ModifiableRedisPositionCache extends InMemoryPositionCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModifiableRedisPositionCache.class);
    protected Map<Long, Long> toStoreInRedis = new ConcurrentHashMap<>();
    private LinesRedisStorage linesRedisStorage;

    private String tableName;

    private Semaphore semaphore = new Semaphore(1);

    private ConcurrentHashMap<String, String> logStash = new ConcurrentHashMap();

    private long redisAutoFlushSize = 30_000;
//    private boolean readInTheBeginning = false;
//    private boolean readDuringTest = false;
//    private boolean moveToCacheOnFlush = true;


    private boolean readInTheBeginning = false;
    private boolean readDuringTest = true;
    private boolean moveToCacheOnFlush = true;
    public ModifiableRedisPositionCache(LinesRedisStorage linesRedisStorage, String tableName) {
        super(5_000_000);
        this.linesRedisStorage = linesRedisStorage;
        this.tableName = tableName;
        if (readInTheBeginning) {
            cache = linesRedisStorage.readTableToGivenMap(tableName, cache);
            LOGGER.info("rows read from redis into " + tableName + ": " + cache.size());
        }
    }

    @Override
    public Long get(long encodedBoard) {
        Long value = super.get(encodedBoard);
        if (value == null) {
            value = toStoreInRedis.get(String.valueOf(encodedBoard));
        }
        if (readDuringTest && value == null) {
            // TODO do wyjebania wyjatek
            try {
                Optional<Long> move = linesRedisStorage.get(tableName, encodedBoard);
                if (move.isPresent()) {
                    return move.get();
                }
            } catch (RuntimeException e) {
                return null;
            }
        }
        return value;
    }

    @Override
    public void put(long encodedBoard, long encodedLine) {
        super.put(encodedBoard, encodedLine);
        if (cache.size() % 1_000_000 == 0 && LOGGER.isDebugEnabled()) {
            String toLog = "progress check: cache size: " + cache.size() + ", total:" + (cache.size() + (binaryStorage.size() * binaryEntrySize));
            if (logStash.putIfAbsent(toLog, toLog) == null) {
                LOGGER.debug(toLog);
            }
        }
    }

    @Override
    public void put(long encodedBoard, long encodedLine, boolean persistable) {
        if (persistable && !cache.containsKey(encodedBoard)) {
            if (toStoreInRedis.size() % 100_000 == 0 && LOGGER.isDebugEnabled()) {
                String toLog = "progress check: toStoreInRedis size " + toStoreInRedis.size();
                if (logStash.putIfAbsent(toLog, toLog) == null) {
                    LOGGER.debug(toLog);
                }
            }
            toStoreInRedis.put(encodedBoard, encodedLine);

            if (redisAutoFlushSize > 0) {
                if (moveToCacheOnFlush) {
                    if (toStoreInRedis.size() > redisAutoFlushSize) {
                        flush();
                    }
                } else {
                    if (toStoreInRedis.size() > 0 && (toStoreInRedis.size() % redisAutoFlushSize == 0)) {
                        flush();
                    }
                }
            }

        } else {
            put(encodedBoard, encodedLine);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (redisAutoFlushSize > 0) {
            flush();
        }
    }

    public void flush() {
        boolean acquired = false;
        try {
            if (acquired = semaphore.tryAcquire(0, TimeUnit.SECONDS)) {
                int size = toStoreInRedis.size();
                LOGGER.info("going to flush " + size + " records to " + tableName);
                storeInRedis(tableName, toStoreInRedis);
                LOGGER.info("flushing " + size + " records to " + tableName + " has been done");
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error: ", e);
        } finally {
            if (acquired) {
                semaphore.release();
            }
        }
    }

    private void storeInRedis(String tableName, Map<Long, Long> map) {
        boolean empty = false;
        Iterator<Map.Entry<Long, Long>> i = map.entrySet().iterator();
        while (!empty) {
            Map<String, String> partMap = new HashMap<>();
            while (i.hasNext()) {
                Map.Entry<Long, Long> entry = i.next();
                partMap.put(entry.getKey().toString(), entry.getValue().toString());
                if (moveToCacheOnFlush) {
                    cache.put(entry.getKey(), entry.getValue());
                    i.remove();
                }
                if (partMap.size() >= 10_000) {
                    break;
                }
            }
            if (partMap.size() > 0) {
                linesRedisStorage.store(tableName, partMap);
            } else {
                empty = true;
            }
        }
    }


    // TODO patent się nie sprawdza
//    private void sieve() {
//        boolean acquired = false;
//        try {
//            //LOGGER.info("sieve is about to start");
//
//            if (acquired = semaphore.tryAcquire(0, TimeUnit.SECONDS)) {
//                LOGGER.info("sieve is starting");
//                Iterator<Map.Entry<Long, Long>> i = cache.entrySet().iterator();
//                while (i.hasNext()) {
//                    i.next();
//                    if (ThreadLocalRandom.current().nextInt(10) == 0) {
//                        i.remove();
//                    }
//                }
//                LOGGER.info("sieve has been finished");
//            } else {
//                //LOGGER.info("sieve has been skipped");
//            }
//        } catch (InterruptedException e) {
//            LOGGER.error("Error: ", e);
//        } finally {
//            if (acquired) {
//                semaphore.release();
//            }
//        }
//    }
}
