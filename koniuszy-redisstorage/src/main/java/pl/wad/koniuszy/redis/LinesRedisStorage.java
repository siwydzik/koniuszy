package pl.wad.koniuszy.redis;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class LinesRedisStorage {
    private RedisTemplate<String, String> redisTemplate;

    public LinesRedisStorage(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void store(String tableName, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(tableName, map);
    }

    public Optional<Long> get(String tableName, Long position) {
        return ofNullable(redisTemplate.opsForHash().get(tableName, position.toString())).map(a -> Long.valueOf(a.toString()));
    }

    public Map<Long, Long> readTableToGivenMap(String tableName, Map<Long, Long> map) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash()
                .scan(tableName, ScanOptions.scanOptions().build());
        while (cursor.hasNext()) {
            Map.Entry entry = cursor.next();
            map.put(Long.valueOf(entry.getKey().toString()), Long.valueOf(entry.getValue().toString()));
        }
        return map;
    }

//    public void createSample(String tableName, String sampleTableName, Function<Long, Boolean> matches) {
//        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash()
//                .scan(tableName, ScanOptions.scanOptions().build());
//        int a = 0;
//        while (cursor.hasNext()) {
//            try {
//                if (matches.apply(Long.valueOf(cursor.next().getKey().toString()))) {
//                    redisTemplate.opsForHash().put(sampleTableName, cursor.next().getKey().toString(), cursor.next().getValue().toString());
//                }
//            } catch (RuntimeException e) {
//                a++;
//            }
//        }
//    }
}
