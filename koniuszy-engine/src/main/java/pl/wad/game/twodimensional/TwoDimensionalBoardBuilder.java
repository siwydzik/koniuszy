package pl.wad.game.twodimensional;

import pl.wad.game.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TwoDimensionalBoardBuilder {
    private Map<InitialPosition, TwoDimensionalPiece> pieces;
    private int x;
    private int y;
    private Color firstMove = Color.WHITE;
    private TwoDimensionalAvailablePositionSet twoDimensionalAvailablePositionSet;

    private BiFunction<Integer, Integer, Integer> singleNumberCalculator;

    private boolean lowFootprint = true;

    public TwoDimensionalBoardBuilder(int x, int y) {
        this.x = x;
        this.y = y;
        this.pieces = new HashMap<>();
    }

    public TwoDimensionalBoardBuilder withFirstMove(Color firstMove) {
        this.firstMove = firstMove;
        return this;
    }

    public TwoDimensionalBoardBuilder withSingleNumberCalculator(BiFunction<Integer, Integer, Integer> calculator) {
        this.singleNumberCalculator = calculator;
        return this;
    }

    public TwoDimensionalBoardBuilder withTwoDimensionalAvailablePositionSet(TwoDimensionalAvailablePositionSet positionSet) {
        this.twoDimensionalAvailablePositionSet = positionSet;
        return this;
    }

    public TwoDimensionalBoardBuilder withPiece(TwoDimensionalPiece piece, int x, int y) {
        TwoDimensionalPiece old = this.pieces.put(new InitialPosition(x, y), piece);
        if (old != null) {
            throw new IllegalArgumentException("Position: " + new InitialPosition(x, y) + " already taken");
        }
        return this;
    }

    public TwoDimensionalBoardBuilder withLowFootprint(boolean lowFootprint) {
        this.lowFootprint = lowFootprint;
        return this;
    }

    public TwoDimensionalBoard build() {
        if (singleNumberCalculator != null && twoDimensionalAvailablePositionSet != null) {
            throw new IllegalStateException("only one from: singleNumberCalculator and twoDimensionalAvailablePositionSet can be set at a time");
        }

        if (twoDimensionalAvailablePositionSet == null) {
            if (singleNumberCalculator == null) {
                singleNumberCalculator = new BlackSquareOnlyNumerator(y);
            }
            this.twoDimensionalAvailablePositionSet = new TwoDimensionalAvailablePositionSet(x, y, singleNumberCalculator);
        }


        if (lowFootprint) {
            return new ArrayTwoDimensionalBoard(pieces, firstMove, twoDimensionalAvailablePositionSet);
        } else {
            MatrixTwoDimensionalBoard board = new MatrixTwoDimensionalBoard(x, y, firstMove, twoDimensionalAvailablePositionSet);
            pieces.entrySet().stream().forEach(e -> {
                board.initPiecePosition(e.getValue(), twoDimensionalAvailablePositionSet.getValidPosition(e.getKey().x(), e.getKey().y()));
            });
            return board;
        }


    }

    public record InitialPosition(int x, int y) {

    }
}
