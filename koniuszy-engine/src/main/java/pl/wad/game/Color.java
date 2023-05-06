package pl.wad.game;

import static java.util.Arrays.asList;

public enum Color {

    BLACK(0), WHITE(1);
    private int numeric;

    Color(int numeric) {
        this.numeric = numeric;
    }

    public int getNumeric() {
        return numeric;
    }

    public static Color fromNumeric(int numeric) {
        return asList(values()).stream().filter(c -> c.getNumeric() == numeric).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown number"));
    }
}
