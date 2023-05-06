package pl.wad.koniuszy;

import pl.wad.game.Color;

import java.util.Map;

public record State(Map<String, String> position, Color winner, Color winningStrategy, Integer movesToWin) {

}
