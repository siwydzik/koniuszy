package pl.wad.game.wolfandsheeps;

import pl.wad.game.twodimensional.TwoDimensionalBoard;
import pl.wad.game.wolfandsheeps.engine.Engine;
import pl.wad.game.wolfandsheeps.engine.Line;
import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet;

import java.util.Map;

public class WolfAndSheepsGame {

    private TwoDimensionalBoard board;

    private WoolfAndSheepsVictory woolfAndSheepsVictory;
    private TwoDimensionalAvailablePositionSet positionSet;


    public WolfAndSheepsGame(TwoDimensionalBoard board, WoolfAndSheepsVictory woolfAndSheepsVictory) {
        this.woolfAndSheepsVictory = woolfAndSheepsVictory;
        this.board = board;
        this.positionSet = board.getPositionSet();

    }

    public Line getBestLine(Engine engine) {
        return engine.getBestLine(board);
    }

    public boolean move(String from, String to) {
        return board.move(positionSet.getValidPosition(from), positionSet.getValidPosition(to));
    }

    public Line cpuMove(Engine engine) {
        Line line = engine.getBestLine(board);
        if (line.nextMove() != null) {
            board.move(line.nextMove().from(), line.nextMove().to());
        }
        return line;
    }

    public Map<String, String> getPosition() {
        return board.getAllPositionsInClassicNotation();
    }

    public Color victoryCheck() {
        return woolfAndSheepsVictory.victoryCheck(board);
    }

    public WoolfAndSheepsVictory getWoolfAndSheepsVictory() {
        return woolfAndSheepsVictory;
    }

    public TwoDimensionalAvailablePositionSet getPositionSet() {
        return board.getPositionSet();
    }

    public String getGameId() {
        return "x" + positionSet.getX() +
                "y" + positionSet.getY() +
                "s" + board.getWhitePieces().size() +
                "w" + board.getBlackPieces().size() +
                "v" + woolfAndSheepsVictory.getAlgorithmId();
    }
}
