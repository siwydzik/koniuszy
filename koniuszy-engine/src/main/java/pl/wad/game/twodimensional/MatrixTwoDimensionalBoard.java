package pl.wad.game.twodimensional;

import pl.wad.game.Color;
import pl.wad.game.Piece;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet.TwoDimensionalPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.wad.game.Color.BLACK;
import static pl.wad.game.Color.WHITE;

public class MatrixTwoDimensionalBoard extends TwoDimensionalBoard {
    private TwoDimensionalPiece[][] fields;
    private List<TwoDimensionalPiece> whitePieces = new ArrayList<>();
    private List<TwoDimensionalPiece> blackPieces = new ArrayList<>();

    MatrixTwoDimensionalBoard(int x, int y, Color nextToMove, TwoDimensionalAvailablePositionSet positions) {
        super(nextToMove, positions);
        this.fields = new TwoDimensionalPiece[x][y];
    }


    MatrixTwoDimensionalBoard initPiecePosition(TwoDimensionalPiece piece, TwoDimensionalPosition position) {
        // TODO ogarnac potencjalne rozspolnienie stanu wewnętetrzneg
        fields[position.x()][position.y()] = piece;
        piece.initiallyPlaceOnBoard(this, position);
        if (piece.getColor() == WHITE) {
            whitePieces.add(piece);
        } else if (piece.getColor() == BLACK) {
            blackPieces.add(piece);
        }
        return this;
    }


    private Piece get(int x, int y) {
        return fields[x][y];
    }

    @Override
    public boolean existAndIsEmpty(int x, int y) {
        if (!positions.exist(x, y)) {
            return false;
        }
        return fields[x][y] == null;
    }

    @Override
    public List<TwoDimensionalPiece> getWhitePieces() {
        return whitePieces;
    }

    @Override
    public List<TwoDimensionalPiece> getBlackPieces() {
        return blackPieces;
    }

    @Override
    public Map<String, String> getAllPositionsInClassicNotation() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                TwoDimensionalPiece piece = fields[i][j];
                if (piece != null) {
                    map.put(piece.getCurrentPosition().getClassicNotation(), piece.getType());
                }
            }
        }
        return map;
    }

    @Override
    public TwoDimensionalPiece get(TwoDimensionalPosition position) {
        return fields[position.x()][position.y()];
    }

    @Override
    public boolean move(TwoDimensionalPosition from, TwoDimensionalPosition to) {
        TwoDimensionalPiece piece = get(from);
        if (piece == null || piece.getColor() != nextToMove) {
            return false;
        }
        if (piece.moveToPosition(to)) {
            fields[from.x()][from.y()] = null;
            fields[to.x()][to.y()] = piece;
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
    public MatrixTwoDimensionalBoard copy() {
        MatrixTwoDimensionalBoard newBoard = new MatrixTwoDimensionalBoard(fields.length, fields[0].length, nextToMove, getPositionSet());
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                TwoDimensionalPiece piece = fields[i][j];
                if (piece != null) {
                    newBoard.initPiecePosition(piece.emptyCopy(), piece.getCurrentPosition());
                }
            }
        }
        return newBoard;
    }

    @Override
    public TwoDimensionalBoardBuilder copyBuilder() {
        TwoDimensionalBoardBuilder builder = new TwoDimensionalBoardBuilder(positions.getX(), positions.getY());
        builder.withTwoDimensionalAvailablePositionSet(positions);
        builder.withLowFootprint(false);
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatrixTwoDimensionalBoard that = (MatrixTwoDimensionalBoard) o;

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
