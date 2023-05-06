package pl.wad.game.twodimensional;

import java.util.function.BiFunction;

public class BlackSquareOnlyNumerator implements BiFunction<Integer, Integer, Integer> {
    private int maxX;

    public BlackSquareOnlyNumerator(int maxX) {
        this.maxX = maxX;
    }

    @Override
    public Integer apply(Integer x, Integer y) {
        if ((x + y) % 2 == 0) {
            return (y * maxX + x) / 2;
        }
        return null;
    }
}
