package pl.wad.game.wolfandsheeps;

import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalBoard;

public class WoolfAndSheepsVictory {

    private int wolfsToGetToTheEnd;

    public WoolfAndSheepsVictory() {
        this(1);
    }

    public WoolfAndSheepsVictory(int wolfsToGetToTheEnd) {
        this.wolfsToGetToTheEnd = wolfsToGetToTheEnd;
    }

    public Color victoryCheck(TwoDimensionalBoard board) {
        if (board.getNextToMove() == Color.BLACK) {
            if (!board.getBlackPieces().stream().map(w -> w.canMove()).filter(Boolean.TRUE::equals).findFirst().orElse(false)) {
                return Color.WHITE;
            }
            return null;
        } else {
            boolean sheepsCanMove = board.getWhitePieces().stream().map(w -> w.canMove()).filter(Boolean.TRUE::equals).findFirst().orElse(false);
            if (sheepsCanMove) {
                if (wolfsToGetToTheEnd <= board.getBlackPieces().stream().map(w -> w.getCurrentPosition().y() == 0).filter(Boolean.TRUE::equals).count()) {
                    return Color.BLACK;
                }
                return null;
            }
            return Color.BLACK;
        }
    }

    public String getAlgorithmId() {
        return "std" + wolfsToGetToTheEnd;
    }
}
