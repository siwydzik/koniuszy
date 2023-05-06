package pl.wad.game.wolfandsheeps.engine.marshall;

import pl.wad.game.Color;
import pl.wad.game.binarystorage.LongDecoder;
import pl.wad.game.binarystorage.LongEncoder;
import pl.wad.game.twodimensional.TwoDimensionalBoard;
import pl.wad.game.wolfandsheeps.engine.Line;
import pl.wad.game.wolfandsheeps.engine.Move;

import static java.util.Optional.ofNullable;
import static pl.wad.game.binarystorage.LongEncoder.bitsConsumption;
import static pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet.TwoDimensionalPosition;


public class LineMarshaller {

    private int MAX_COUNT = 1024;

    private int pieceBitsConsumption;
    private int colorBitConsumption;

    private int countBitConsumption;

    public LineMarshaller(int maxSingleNumber) {
        this.pieceBitsConsumption = bitsConsumption(maxSingleNumber);
        this.colorBitConsumption = bitsConsumption(Color.values().length);
        this.countBitConsumption = bitsConsumption(MAX_COUNT);
    }

    public long marshall(Line line) {

        if (line.count() >= 1024) {
            throw new IllegalArgumentException("count must be smaller then " + 1024);
        }

        LongEncoder longEncoder = new LongEncoder()
                .addToEncoded(line.winningColor().getNumeric(), colorBitConsumption)
                .addToEncoded(ofNullable(line.nextMove()).map(Move::to).map(TwoDimensionalPosition::singleNumber).orElse(0), pieceBitsConsumption)
                .addToEncoded(ofNullable(line.nextMove()).map(Move::from).map(TwoDimensionalPosition::singleNumber).orElse(0), pieceBitsConsumption)
                .addToEncoded(line.count(), countBitConsumption);

        return longEncoder.getEncoded();
    }

    public Line unmarshall(long encoded, TwoDimensionalBoard board) {

        LongDecoder longEncoder = new LongDecoder(encoded);
        int count = (int) longEncoder.readFromEncoded(countBitConsumption);
        int from = (int) longEncoder.readFromEncoded(pieceBitsConsumption);
        int to = (int) longEncoder.readFromEncoded(pieceBitsConsumption);
        Color color = Color.fromNumeric((int) longEncoder.readFromEncoded(colorBitConsumption));

        Move move = new Move(board.getPositionSet().getBySingleNumber(from), board.getPositionSet().getBySingleNumber(to));
        Line line = new Line(color, count, move);

        return line;
    }


}
