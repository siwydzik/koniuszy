package pl.wad.game.twodimensional;

import pl.wad.game.Board;
import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet.TwoDimensionalPosition;

import java.util.Collection;


public abstract class TwoDimensionalBoard implements Board<TwoDimensionalPosition> {
    protected final TwoDimensionalAvailablePositionSet positions;
    protected Color nextToMove;

    TwoDimensionalBoard(Color nextToMove, TwoDimensionalAvailablePositionSet positions) {
        this.positions = positions;
        this.nextToMove = nextToMove;
    }


    public TwoDimensionalAvailablePositionSet getPositionSet() {
        return positions;
    }

    @Override
    public Color getNextToMove() {
        return nextToMove;
    }


    public abstract boolean existAndIsEmpty(int x, int y);

    public abstract Collection<TwoDimensionalPiece> getWhitePieces();

    public abstract Collection<TwoDimensionalPiece> getBlackPieces();

    public abstract TwoDimensionalBoard copy();

    public abstract TwoDimensionalBoardBuilder copyBuilder();

}
