package pl.wad.game.wolfandsheeps.engine;

import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet;

public record Move(TwoDimensionalAvailablePositionSet.TwoDimensionalPosition from, TwoDimensionalAvailablePositionSet.TwoDimensionalPosition to) {
}
