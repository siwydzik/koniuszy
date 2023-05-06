package pl.wad.game.wolfandsheeps.engine.marshall;

import pl.wad.game.Color;
import pl.wad.game.binarystorage.LongDecoder;
import pl.wad.game.binarystorage.LongEncoder;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet;
import pl.wad.game.twodimensional.TwoDimensionalBoard;
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder;
import pl.wad.game.wolfandsheeps.Sheep;
import pl.wad.game.wolfandsheeps.Wolf;

import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;
import static pl.wad.game.binarystorage.LongEncoder.bitsConsumption;


public class BoardMarshaller {

    private int pieceBitsConsumption;
    private int colorBitConsumption;

    public BoardMarshaller(int maxSingleNumber) {
        this.pieceBitsConsumption = bitsConsumption(maxSingleNumber);
        this.colorBitConsumption = bitsConsumption(Color.values().length);
    }

    public long marshall(TwoDimensionalBoard board) {
        ArrayList<Integer> list = new ArrayList<>(board.getWhitePieces().size() + board.getBlackPieces().size());
        board.getWhitePieces().stream().map(p -> p.getCurrentPosition().singleNumber()).sorted().collect(toCollection(() -> list));
        board.getBlackPieces().stream().map(p -> p.getCurrentPosition().singleNumber()).sorted().collect(toCollection(() -> list));
        LongEncoder longEncoder = new LongEncoder();
        for (Integer val : list) {
            longEncoder.addToEncoded(val, pieceBitsConsumption);
        }
        longEncoder.addToEncoded(board.getNextToMove().getNumeric(), colorBitConsumption);
        return longEncoder.getEncoded();
    }

    public TwoDimensionalBoard unmarshall(long encoded, TwoDimensionalBoard board) {
        TwoDimensionalBoardBuilder builder = board.copyBuilder();
        LongDecoder longDecoder = new LongDecoder(encoded);

        long color = longDecoder.readFromEncoded(colorBitConsumption);
        builder.withFirstMove(Color.fromNumeric((int) color));

        for (int i = 0; i < board.getBlackPieces().size(); i++) {
            long numericPosition = longDecoder.readFromEncoded(pieceBitsConsumption);
            TwoDimensionalAvailablePositionSet.TwoDimensionalPosition position = board.getPositionSet().getBySingleNumber((int) numericPosition);
            builder.withPiece(new Wolf(), position.x(), position.y());
        }

        for (int i = 0; i < board.getWhitePieces().size(); i++) {
            long numericPosition = longDecoder.readFromEncoded(pieceBitsConsumption);
            TwoDimensionalAvailablePositionSet.TwoDimensionalPosition position = board.getPositionSet().getBySingleNumber((int) numericPosition);
            builder.withPiece(new Sheep(), position.x(), position.y());
        }

        return builder.build();
    }

}
