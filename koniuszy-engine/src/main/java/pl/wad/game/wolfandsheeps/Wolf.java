package pl.wad.game.wolfandsheeps;

import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalPiece;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet.TwoDimensionalPosition;

import java.util.ArrayList;
import java.util.List;

import static pl.wad.game.Color.BLACK;

public class Wolf extends TwoDimensionalPiece {

    @Override
    public TwoDimensionalPosition getCurrentPosition() {
        return currentPosition;
    }


    @Override
    public boolean moveToPosition(TwoDimensionalPosition newPosition) {
        if (isMoveAllowed(newPosition)) {
            currentPosition = newPosition;
            return true;
        }
        return false;
    }

    @Override
    public boolean isMoveAllowed(TwoDimensionalPosition newPosition) {
        if (board.get(newPosition) == null) {
            if (currentPosition.x() + 1 == newPosition.x()) {
                return currentPosition.y() + 1 == newPosition.y() || currentPosition.y() - 1 == newPosition.y();
            } else if (currentPosition.x() - 1 == newPosition.x()) {
                return currentPosition.y() + 1 == newPosition.y() || currentPosition.y() - 1 == newPosition.y();
            }
        }
        return false;
    }

    @Override
    public boolean canMove() {
        return board.existAndIsEmpty(currentPosition.x() - 1, currentPosition.y() - 1) ||
                board.existAndIsEmpty(currentPosition.x() - 1, currentPosition.y() + 1) ||
                board.existAndIsEmpty(currentPosition.x() + 1, currentPosition.y() - 1) ||
                board.existAndIsEmpty(currentPosition.x() + 1, currentPosition.y() + 1);
    }

    @Override
    public List<TwoDimensionalPosition> getPossibleMoves() {
        TwoDimensionalPosition pos1 = board.getPositionSet().getValidPosition(currentPosition.x() + 1, currentPosition.y() - 1);
        TwoDimensionalPosition pos2 = board.getPositionSet().getValidPosition(currentPosition.x() + 1, currentPosition.y() + 1);
        TwoDimensionalPosition pos3 = board.getPositionSet().getValidPosition(currentPosition.x() - 1, currentPosition.y() - 1);
        TwoDimensionalPosition pos4 = board.getPositionSet().getValidPosition(currentPosition.x() - 1, currentPosition.y() + 1);
        List<TwoDimensionalPosition> result = new ArrayList(4);
        if (pos1 != null && board.get(pos1) == null) {
            result.add(pos1);
        }
        if (pos2 != null && board.get(pos2) == null) {
            result.add(pos2);
        }
        if (pos3 != null && board.get(pos3) == null) {
            result.add(pos3);
        }
        if (pos4 != null && board.get(pos4) == null) {
            result.add(pos4);
        }
        return result;
    }

    @Override
    public Color getColor() {
        return BLACK;
    }

    @Override
    public String getType() {
        return "bN";
    }

    @Override
    public Wolf emptyCopy() {
        return new Wolf();
    }

}
