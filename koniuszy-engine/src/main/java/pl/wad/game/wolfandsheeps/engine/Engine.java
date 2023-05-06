package pl.wad.game.wolfandsheeps.engine;

import pl.wad.game.twodimensional.TwoDimensionalBoard;

public interface Engine {
    Line getBestLine(TwoDimensionalBoard board);
}
