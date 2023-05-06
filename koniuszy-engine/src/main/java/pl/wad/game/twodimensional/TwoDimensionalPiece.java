package pl.wad.game.twodimensional;

import pl.wad.game.Piece;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet.TwoDimensionalPosition;

public abstract class TwoDimensionalPiece implements Piece<TwoDimensionalPosition, TwoDimensionalBoard> {

    protected TwoDimensionalBoard board;
    protected TwoDimensionalPosition currentPosition;

    public TwoDimensionalBoard getBoard() {
        return board;
    }

    @Override
    public void initiallyPlaceOnBoard(TwoDimensionalBoard board, TwoDimensionalPosition position) {
        this.board = board;
        this.currentPosition = position;
        if (currentPosition.x() % 2 != currentPosition.y() % 2) {
            throw new IllegalArgumentException("Must be placed on black square");
        }
    }

    public abstract TwoDimensionalPiece emptyCopy();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwoDimensionalPiece that = (TwoDimensionalPiece) o;

        return getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return getType().hashCode();
    }

    @Override
    public String toString() {
        return "TwoDimensionalPiece{" +
                "currentPosition=" + currentPosition +
                '}';
    }
}
