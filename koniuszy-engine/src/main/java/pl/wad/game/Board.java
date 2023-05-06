package pl.wad.game;

import java.util.Map;

public interface Board<P extends Position> {
    Piece get(P position);

    boolean move(P from, P to);

    Map<String, String> getAllPositionsInClassicNotation();

    Color getNextToMove();
}
