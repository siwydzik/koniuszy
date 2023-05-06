package pl.wad.game.wolfandsheeps.engine.positioncache;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

class BinaryStorageEntry {
    private long positions[];
    private int lines[];

    BinaryStorageEntry(TreeMap<Long, Long> map) {
        positions = new long[map.size()];
        lines = new int[map.size()];
        int i = 0;
        for (Map.Entry<Long, Long> e : map.entrySet()) {
            positions[i] = e.getKey();
            lines[i] = Math.toIntExact(e.getValue());
            i++;
        }
    }

    public Long getLine(long position) {
        int i = Arrays.binarySearch(positions, position);
        if (i > 0) {
            return Long.valueOf(lines[i]);
        }
        return null;
    }
}
