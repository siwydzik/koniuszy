package pl.wad.game;

import java.util.List;

public interface Piece<P extends Position, B extends Board<? extends Position>> {
    P getCurrentPosition();
    boolean moveToPosition(P newPosition);

    boolean isMoveAllowed(P newPosition);

    List<P> getPossibleMoves();

    boolean canMove();

    Color getColor();

    String getType();

    void initiallyPlaceOnBoard(B board, P position);

    Piece<P , B > emptyCopy();
}
