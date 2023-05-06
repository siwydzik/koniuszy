package pl.wad.game.wolfandsheeps.engine;

import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet;
import pl.wad.game.twodimensional.TwoDimensionalBoard;
import pl.wad.game.wolfandsheeps.engine.marshall.BoardMarshaller;
import pl.wad.game.wolfandsheeps.engine.marshall.LineMarshaller;
import pl.wad.game.wolfandsheeps.engine.positioncache.PositionCache;


public class Table implements Engine {

    private PositionCache positionCache;
    private BoardMarshaller boardMarshaller;
    private LineMarshaller lineMarshaller;

    public Table(TwoDimensionalAvailablePositionSet twoDimensionalAvailablePositionSet, PositionCache positionCache) {
        this.boardMarshaller = new BoardMarshaller(twoDimensionalAvailablePositionSet.getMaxSingleNumber());
        this.lineMarshaller = new LineMarshaller(twoDimensionalAvailablePositionSet.getMaxSingleNumber());
        this.positionCache = positionCache;
    }

    @Override
    public Line getBestLine(TwoDimensionalBoard board) {
        return lineMarshaller.unmarshall(positionCache.get(boardMarshaller.marshall(board)), board);
    }

}

