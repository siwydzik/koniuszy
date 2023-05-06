package pl.wad.game.wolfandsheeps.engine;

import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet;
import pl.wad.game.twodimensional.TwoDimensionalBoard;
import pl.wad.game.twodimensional.TwoDimensionalPiece;
import pl.wad.game.wolfandsheeps.WolfAndSheepsGame;
import pl.wad.game.wolfandsheeps.WoolfAndSheepsVictory;
import pl.wad.game.wolfandsheeps.engine.marshall.BoardMarshaller;
import pl.wad.game.wolfandsheeps.engine.marshall.LineMarshaller;
import pl.wad.game.wolfandsheeps.engine.positioncache.ModifiablePositionCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class BruteForce implements Engine {

    private ModifiablePositionCache positionCache;
    private WoolfAndSheepsVictory woolfAndSheepsVictory;
    private BoardMarshaller boardMarshaller;
    private LineMarshaller lineMarshaller;
    private TwoDimensionalAvailablePositionSet positionSet;
    private Function<TwoDimensionalBoard, Boolean> toSave;

    public BruteForce(TwoDimensionalBoard board, WoolfAndSheepsVictory woolfAndSheepsVictory, ModifiablePositionCache positionCache, Function<TwoDimensionalBoard, Boolean> toSave) {
        this.boardMarshaller = new BoardMarshaller(board.getPositionSet().getMaxSingleNumber());
        this.lineMarshaller = new LineMarshaller(board.getPositionSet().getMaxSingleNumber());
        this.woolfAndSheepsVictory = woolfAndSheepsVictory;
        this.positionCache = positionCache;
        this.positionSet = board.getPositionSet();
        this.toSave = toSave;
    }

    public BruteForce(TwoDimensionalAvailablePositionSet positionSet, WoolfAndSheepsVictory woolfAndSheepsVictory, ModifiablePositionCache positionCache) {
        this.boardMarshaller = new BoardMarshaller(positionSet.getMaxSingleNumber());
        this.lineMarshaller = new LineMarshaller(positionSet.getMaxSingleNumber());
        this.woolfAndSheepsVictory = woolfAndSheepsVictory;
        this.positionCache = positionCache;
        this.positionSet = positionSet;
        this.toSave = a -> false;
    }

    public BruteForce(WolfAndSheepsGame wolfAndSheepsGame, ModifiablePositionCache positionCache) {
        this.positionSet = wolfAndSheepsGame.getPositionSet();
        this.boardMarshaller = new BoardMarshaller(positionSet.getMaxSingleNumber());
        this.lineMarshaller = new LineMarshaller(positionSet.getMaxSingleNumber());
        this.woolfAndSheepsVictory = wolfAndSheepsGame.getWoolfAndSheepsVictory();
        this.positionCache = positionCache;
        this.toSave = a -> false;
    }

    @Override
    public Line getBestLine(TwoDimensionalBoard board) {
        return analisePosition(board);
    }

    public Line analisePosition(TwoDimensionalBoard board) {
        if (!board.getPositionSet().equals(positionSet)) {
            throw new IllegalArgumentException("Given board doesn't match the board provided in constructor.");
        }
        Line line = analisePosition(board, 0);
        positionCache.finish();
        return line;
    }

    public Line analisePositionParallel(TwoDimensionalBoard board) {
        if (!board.getPositionSet().equals(positionSet)) {
            throw new IllegalArgumentException("Given board doesn't match the board provided in constructor.");
        }
        RecursiveTask<Line> customRecursiveTask = new BruteForceRecursiveTask(board, 0, null);
        ForkJoinPool.commonPool().execute(customRecursiveTask);
        Line line = customRecursiveTask.join();
        positionCache.finish();
        return line;
    }

    private Line analisePosition(TwoDimensionalBoard board, int level) {
        Long encodedBoard = boardMarshaller.marshall(board);
        Long encodedOptimalChoice = positionCache.get(encodedBoard);
        if (encodedOptimalChoice != null) {
            return lineMarshaller.unmarshall(encodedOptimalChoice, board);
        }
        Line cachedLine;

        Color color = woolfAndSheepsVictory.victoryCheck(board);
        if (color != null) {
            cachedLine = new Line(color, 0, null);
        } else {
            Line bestSoFar = null;
            List<Line> all = new ArrayList<>();
            List<Move> allMoves = getAllMoves(board);
            Collections.reverse(allMoves);
            for (Move move : allMoves) {
                TwoDimensionalBoard newBoard = board.copy();
                newBoard.move(move.from(), move.to());
                Line line = analisePosition(newBoard, level + 1);
                all.add(line);
                bestSoFar = getBetterPath(board.getNextToMove(), move, line, bestSoFar);
            }
            cachedLine = bestSoFar;
        }
        positionCache.put(encodedBoard, lineMarshaller.marshall(cachedLine), toSave.apply(board));
        return cachedLine;
    }

    private Line getBetterPath(Color colorToMove, Move move, Line current, Line bestSoFar) {
        Line newBestLine = null;
        if (bestSoFar == null) {
            newBestLine = current;
        } else if (current.winningColor() == colorToMove && bestSoFar.winningColor() != colorToMove) {
            newBestLine = current;
        } else if (current.winningColor() == colorToMove && bestSoFar.winningColor() == colorToMove && current.count() < bestSoFar.count()) {
            newBestLine = current;
        } else if (current.winningColor() != colorToMove && bestSoFar.winningColor() != colorToMove && current.count() > bestSoFar.count()) {
            newBestLine = current;
        }
        if (newBestLine != null) {
            return new Line(newBestLine.winningColor(), newBestLine.count() + 1, move);
        }
        return bestSoFar;
    }

    private List<Move> getAllMoves(TwoDimensionalBoard board) {
        if (board.getNextToMove() == Color.WHITE) {
            return getAllMoves(board.getWhitePieces());
        } else if (board.getNextToMove() == Color.BLACK) {
            return getAllMoves(board.getBlackPieces());
        }
        return Collections.EMPTY_LIST;
    }

    private List<Move> getAllMoves(Collection<TwoDimensionalPiece> pieces) {
        List<Move> moves = new ArrayList<>();
        for (TwoDimensionalPiece piece : pieces) {
            moves.addAll(piece.getPossibleMoves().stream().map(p -> new Move(piece.getCurrentPosition(), p)).collect(toList()));
        }
        return moves;
    }

    public class BruteForceRecursiveTask extends RecursiveTask<Line> {

        private TwoDimensionalBoard board;
        private int level;
        private Move moveThatLeadToThePosition;

        public BruteForceRecursiveTask(TwoDimensionalBoard board, int level, Move moveThatLeadToThePosition) {
            this.board = board;
            this.level = level;
            this.moveThatLeadToThePosition = moveThatLeadToThePosition;
        }

        @Override
        protected Line compute() {
            long encodedBoard = boardMarshaller.marshall(board);
            Long encodedOptimalChoice = positionCache.get(encodedBoard);
            if (encodedOptimalChoice != null) {
                return lineMarshaller.unmarshall(encodedOptimalChoice, board);
            }
            Line cachedLine;

            Color color = woolfAndSheepsVictory.victoryCheck(board);
            if (color != null) {
                cachedLine = new Line(color, 0, null);
            } else {
                Line bestSoFar = null;
                List<BruteForceRecursiveTask> all = new ArrayList<>();
                for (Move move : getAllMoves(board)) {
                    TwoDimensionalBoard newBoard = board.copy();
                    newBoard.move(move.from(), move.to());
                    all.add(new BruteForceRecursiveTask(newBoard, level + 1, move));
                }
                for (BruteForceRecursiveTask forkJoinTask : ForkJoinTask.invokeAll(all)) {
                    Line line = forkJoinTask.join();
                    bestSoFar = getBetterPath(board.getNextToMove(), forkJoinTask.moveThatLeadToThePosition, line, bestSoFar);
                }
                cachedLine = bestSoFar;
            }
            positionCache.put(encodedBoard, lineMarshaller.marshall(cachedLine), toSave.apply(board));
            return cachedLine;
        }
    }
}

