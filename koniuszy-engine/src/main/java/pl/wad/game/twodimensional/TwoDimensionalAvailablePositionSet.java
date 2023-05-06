package pl.wad.game.twodimensional;

import pl.wad.game.Position;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class TwoDimensionalAvailablePositionSet {

    private TwoDimensionalPosition[][] positions;
    private Map<Integer, TwoDimensionalPosition> singleNumberMap = new HashMap<>();
    private int maxSingleNumber;


    public TwoDimensionalAvailablePositionSet(int x, int y, BiFunction<Integer, Integer, Integer> singleNumberCalculator) {
        this.positions = new TwoDimensionalPosition[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Integer singleNumber = singleNumberCalculator.apply(i, j);
                positions[i][j] = new TwoDimensionalPosition(i, j, singleNumber);
                if (singleNumber != null) {
                    if (singleNumber > maxSingleNumber) {
                        maxSingleNumber = singleNumber;
                    }
                    singleNumberMap.put(singleNumber, positions[i][j]);
                }
            }
        }
    }

    public TwoDimensionalPosition getValidPosition(String position) {
        if (position.length() != 2) {
            return null;
        }
        return getValidPosition(position.charAt(0) - 'a', position.charAt(1) - '1');
    }

    public TwoDimensionalPosition getValidPosition(int x, int y) {
        if (!exist(x, y)) {
            return null;
        }
        return positions[x][y];
    }

    public int getX() {
        return positions.length;
    }

    public int getY() {
        return positions[0].length;
    }

    public boolean exist(int x, int y) {
        if (x < 0 || x >= positions.length || y < 0 || y >= positions[0].length) {
            return false;
        }
        return true;
    }

    public int getMaxSingleNumber() {
        return maxSingleNumber;
    }

    public TwoDimensionalPosition getBySingleNumber(int singleNumber) {
        return singleNumberMap.get(singleNumber);
    }

    public record TwoDimensionalPosition(int x, int y, Integer singleNumber) implements Position {

        public String getClassicNotation() {
            return Character.toString((char) (x + 'a')) + Integer.valueOf(y + 1);
        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    ", singleNumber=" + singleNumber +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwoDimensionalAvailablePositionSet that = (TwoDimensionalAvailablePositionSet) o;

        if (getMaxSingleNumber() != that.getMaxSingleNumber()) return false;
        if (!Arrays.deepEquals(positions, that.positions)) return false;
        return Objects.equals(singleNumberMap, that.singleNumberMap);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(positions);
        result = 31 * result + (singleNumberMap != null ? singleNumberMap.hashCode() : 0);
        result = 31 * result + getMaxSingleNumber();
        return result;
    }
}
