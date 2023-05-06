package pl.wad.game.wolfandsheeps.engine.positioncache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public class BinaryStorage {
    private volatile List<BinaryStorageEntry> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void putEntry(TreeMap map) {
        List<BinaryStorageEntry> newList = new ArrayList<>(list);
        newList.add(new BinaryStorageEntry(map));
        list = newList;
    }

    public Long find(long position) {
        return list.stream()
                .map(e -> e.getLine(position))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
