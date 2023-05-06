package pl.wad.game.twodimensional;

import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet.TwoDimensionalPosition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.wad.game.Color.BLACK;
import static pl.wad.game.Color.WHITE;

public class ArrayTwoDimensionalBoard extends TwoDimensionalBoard {
    private TwoDimensionalPiece[] fields;

    ArrayTwoDimensionalBoard(Map<TwoDimensionalBoardBuilder.InitialPosition, TwoDimensionalPiece> pieces, Color nextToMove, TwoDimensionalAvailablePositionSet positions) {
        super(nextToMove, positions);
        this.fields = new TwoDimensionalPiece[pieces.size()];
        int i = 0;
        for (Map.Entry<TwoDimensionalBoardBuilder.InitialPosition, TwoDimensionalPiece> e : pieces.entrySet()) {
            TwoDimensionalPosition position = positions.getValidPosition(e.getKey().x(), e.getKey().y());
            e.getValue().initiallyPlaceOnBoard(this, position);
            fields[i++] = e.getValue();
        }
    }

    ArrayTwoDimensionalBoard(TwoDimensionalPiece[] fieldsToCopyFrom, Color nextToMove, TwoDimensionalAvailablePositionSet positions) {
        super(nextToMove, positions);
        TwoDimensionalPiece[] fields = new TwoDimensionalPiece[fieldsToCopyFrom.length];
        for (int i = 0; i < fields.length && fieldsToCopyFrom[i] != null; i++) {
            fields[i] = fieldsToCopyFrom[i].emptyCopy();
        }
        this.fields = fields;
        for (int i = 0; i < fields.length && fields[i] != null; i++) {
            fields[i].initiallyPlaceOnBoard(this, fieldsToCopyFrom[i].getCurrentPosition());
        }
    }


    private TwoDimensionalPiece get(int x, int y) {
        for (int i = 0; i < fields.length && fields[i] != null; i++) {
            if (fields[i].getCurrentPosition() == positions.getValidPosition(x, y)) {
                return fields[i];
            }
        }
        return null;
    }

    @Override
    public boolean existAndIsEmpty(int x, int y) {
        if (!positions.exist(x, y)) {
            return false;
        }
        return get(x, y) == null;
    }

    @Override
    public List<TwoDimensionalPiece> getWhitePieces() {
        return Arrays.asList(fields).stream().filter(p -> p.getColor() == WHITE).collect(Collectors.toList());
    }

    @Override
    public List<TwoDimensionalPiece> getBlackPieces() {
        return Arrays.asList(fields).stream().filter(p -> p.getColor() == BLACK).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getAllPositionsInClassicNotation() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < fields.length && fields[i] != null; i++) {
            map.put(fields[i].getCurrentPosition().getClassicNotation(), fields[i].getType());
        }
        return map;
    }

    @Override
    public TwoDimensionalPiece get(TwoDimensionalPosition position) {
        return get(position.x(), position.y());
    }

    @Override
    public boolean move(TwoDimensionalPosition from, TwoDimensionalPosition to) {
        TwoDimensionalPiece piece = get(from);
        if (piece == null || piece.getColor() != nextToMove) {
            return false;
        }
        if (piece.moveToPosition(to)) {
            nextToMove = nextToMove == WHITE ? BLACK : WHITE;
            return true;
        }
        return false;
    }

    @Override
    public Color getNextToMove() {
        return nextToMove;
    }


    @Override
    public ArrayTwoDimensionalBoard copy() {
        return new ArrayTwoDimensionalBoard(fields, getNextToMove(), getPositionSet());
    }

    @Override
    public TwoDimensionalBoardBuilder copyBuilder() {
        TwoDimensionalBoardBuilder builder = new TwoDimensionalBoardBuilder(positions.getX(), positions.getY());
        builder.withTwoDimensionalAvailablePositionSet(positions);
        builder.withLowFootprint(true);
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTwoDimensionalBoard that = (ArrayTwoDimensionalBoard) o;

        if (!Arrays.deepEquals(fields, that.fields)) return false;
        return nextToMove == that.nextToMove;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(fields);
        result = 31 * result + (nextToMove != null ? nextToMove.hashCode() : 0);
        return result;
    }

}
