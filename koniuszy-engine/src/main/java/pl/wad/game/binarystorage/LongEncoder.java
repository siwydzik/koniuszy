package pl.wad.game.binarystorage;

public class LongEncoder {
    private long encoded;
    private long totalConsumption;

    public LongEncoder() {
    }

    public LongEncoder addToEncoded(long value, int consumption) {
        totalConsumption += consumption;
        if (totalConsumption >= 63) {
            throw new IllegalArgumentException("Long is not enough to store board position");
        }
        encoded = encoded << consumption;
        encoded += value;
        return this;
    }

    public long getEncoded() {
        return encoded;
    }

    public long getTotalConsumption() {
        return totalConsumption;
    }

    public static int bitsConsumption(int n) {
        int count = 0;
        n = n - 1;
        while (n != 0) {
            n >>= 1;
            count += 1;
        }
        return count;
    }
}
