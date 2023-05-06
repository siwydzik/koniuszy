package pl.wad.game.wolfandsheeps.engine;

import pl.wad.game.Color;

public record Line(Color winningColor, Integer count, Move nextMove) {
}
