package pl.wad.game.binarystorage;

public class LongDecoder {
    private long encoded;

    public LongDecoder(long encoded) {
        this.encoded = encoded;
    }

    public long readFromEncoded(int consumption) {
        long mask = (1 << consumption) - 1;
        long value = encoded & mask;
        encoded = encoded >> consumption;
        return value;
    }

}
