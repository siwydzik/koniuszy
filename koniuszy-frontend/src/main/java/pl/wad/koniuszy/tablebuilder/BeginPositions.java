package pl.wad.koniuszy.tablebuilder;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet;
import pl.wad.game.twodimensional.TwoDimensionalBoard;
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder;
import pl.wad.game.twodimensional.TwoDimensionalPiece;
import pl.wad.game.wolfandsheeps.Sheep;
import pl.wad.game.wolfandsheeps.Wolf;
import pl.wad.game.wolfandsheeps.WolfAndSheepsGame;
import pl.wad.game.wolfandsheeps.WoolfAndSheepsVictory;
import pl.wad.game.wolfandsheeps.engine.BruteForce;
import pl.wad.game.wolfandsheeps.engine.Line;
import pl.wad.game.wolfandsheeps.engine.marshall.BoardMarshaller;
import pl.wad.game.wolfandsheeps.engine.marshall.LineMarshaller;
import pl.wad.koniuszy.redis.LinesRedisStorage;
import pl.wad.koniuszy.redis.ModifiableRedisPositionCache;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class BeginPositions {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeginPositions.class);

    @Autowired
    private LinesRedisStorage linesRedisStorage;

    @PostConstruct
    public void init() {
        runAll(Color.BLACK);
        runAll(Color.WHITE);
    }

    public void runAll(Color color) {

        ModifiableRedisPositionCache cache = null;
        for (int i = 1; i <= 7; i += 2) {
            LOGGER.info("get1w4sBoard: " + color + " " + i);
            cache = runRest(get1w4sBoard(color, i), 1, cache);
            LOGGER.info("get1w4sBoard: " + color + " " + i + " END");
        }

        cache = null;
        for (int i = 3; i <= 7; i += 2) {
            for (int j = 1; j < i; j += 2) {
                LOGGER.info("get2w4sBoard: " + color + " " + i + " " + j + " std1");
                cache = runRest(get2w4sBoard(color, i, j), 1, cache);
                LOGGER.info("get2w4sBoard: " + color + " " + i + " std1 END");
            }
        }

        cache = null;
        for (int i = 3; i <= 7; i += 2) {
            for (int j = 1; j < i; j += 2) {
                LOGGER.info("get2w4sBoard: " + color + " " + i + " " + j + " std2");
                cache = runRest(get2w4sBoard(color, i, j), 2, cache);
                LOGGER.info("get2w4sBoard: " + color + " " + i + " std2 END");
            }
        }

        cache = null;
        for (int i = 3; i <= 7; i += 2) {
            for (int j = 1; j < i; j += 2) {
                for (int k = 1; k <= 7; k += 2) {
                    LOGGER.info("get2w5sBoard: " + color + " " + i + " " + j + " " + k + " std1");
                    cache = runRest(get2w5sBoard(color, i, j, k), 1, cache);
                    LOGGER.info("get2w5sBoard: " + color + " " + i + " " + j + " " + k + " std1 END");
                }
            }
        }

        cache = null;
        for (int i = 3; i <= 7; i += 2) {
            for (int j = 1; j < i; j += 2) {
                for (int k = 1; k <= 7; k += 2) {
                    LOGGER.info("get2w5sBoard: " + color + " " + i + " " + j + " " + k + " std2");
                    cache = runRest(get2w5sBoard(color, i, j, k), 2, cache);
                    LOGGER.info("get2w5sBoard: " + color + " " + i + " " + j + " " + k + " std2 END");
                }
            }
        }


        cache = null;
        for (int i = 3; i <= 7; i += 2) {
            for (int j = 1; j < i; j += 2) {
                for (int k = 3; k <= 7; k += 2) {
                    for (int l = 1; l < k; l += 2) {
                        LOGGER.info("get2w6sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " std1");
                        cache = runRest(get2w6sBoard(color, i, j, k, l), 1, cache);
                        LOGGER.info("get2w6sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " std1 END");
                    }
                }
            }
        }

        cache = null;
        for (int i = 3; i <= 7; i += 2) {
            for (int j = 1; j < i; j += 2) {
                for (int k = 3; k <= 7; k += 2) {
                    for (int l = 1; l < k; l += 2) {
                        LOGGER.info("get2w6sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " std2");
                        cache = runRest(get2w6sBoard(color, i, j, k, l), 2, cache);
                        LOGGER.info("get2w6sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " std2 END");
                    }
                }
            }
        }

//        cache = null;
//        for (int i = 3; i <= 7; i += 2) {
//            for (int j = 1; j < i; j += 2) {
//                for (int k = 5; k <= 7; k += 2) {
//                    for (int l = 3; l < k; l += 2) {
//                        for (int m = 1; m < l; m += 2) {
//                            LOGGER.info("get2w7sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " " + m + " std1");
//                            cache = runRest(get2w7sBoard(color, i, j, k, l, m), 1, cache);
//                            LOGGER.info("get2w7sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " " + m + " std1 END");
//                        }
//                    }
//                }
//            }
//        }
//
//        cache = null;
//        for (int i = 3; i <= 7; i += 2) {
//            for (int j = 1; j < i; j += 2) {
//                for (int k = 5; k <= 7; k += 2) {
//                    for (int l = 3; l < k; l += 2) {
//                        for (int m = 1; m < l; m += 2) {
//                            LOGGER.info("get2w7sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " " + m + " std2");
//                            cache = runRest(get2w7sBoard(color, i, j, k, l, m), 2, cache);
//                            LOGGER.info("get2w7sBoard: " + color + " " + i + " " + j + " " + k + " " + l + " " + m + " std2 END");
//                        }
//                    }
//                }
//            }
//        }

    }

    private TwoDimensionalBoard get1w4sBoard(Color color, int wolfX) {
        final TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(color)
                .withPiece(new Wolf(), wolfX, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .build();
        return board;
    }

    private TwoDimensionalBoard get2w4sBoard(Color color, int wolf1X, int wolf2X) {
        final TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(color)
                .withPiece(new Wolf(), wolf1X, 7)
                .withPiece(new Wolf(), wolf2X, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .build();
        return board;
    }

    private TwoDimensionalBoard get2w5sBoard(Color color, int wolf1X, int wolf2X, int sheep1X) {
        final TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(color)
                .withPiece(new Wolf(), wolf1X, 7)
                .withPiece(new Wolf(), wolf2X, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .withPiece(new Sheep(), sheep1X, 1)
                .build();
        return board;
    }

    private TwoDimensionalBoard get2w6sBoard(Color color, int wolf1X, int wolf2X, int sheep1X, int sheep2X) {
        final TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(color)
                .withPiece(new Wolf(), wolf1X, 7)
                .withPiece(new Wolf(), wolf2X, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .withPiece(new Sheep(), sheep1X, 1)
                .withPiece(new Sheep(), sheep2X, 1)
                .build();
        return board;
    }

    private TwoDimensionalBoard get2w7sBoard(Color color, int wolf1X, int wolf2X, int sheep1X, int sheep2X, int sheep3X) {
        final TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(color)
                .withPiece(new Wolf(), wolf1X, 7)
                .withPiece(new Wolf(), wolf2X, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .withPiece(new Sheep(), sheep1X, 1)
                .withPiece(new Sheep(), sheep2X, 1)
                .withPiece(new Sheep(), sheep3X, 1)
                .build();
        return board;
    }

    private ModifiableRedisPositionCache runRest(TwoDimensionalBoard board, int wolfsToWin, ModifiableRedisPositionCache cache) {

        final int initialSum = board.getWhitePieces().stream()
                .map(TwoDimensionalPiece::getCurrentPosition)
                .map(TwoDimensionalAvailablePositionSet.TwoDimensionalPosition::y)
                .reduce(0, (a, b) -> a + b);
        Color initialColor = board.getNextToMove();

        WoolfAndSheepsVictory victory = new WoolfAndSheepsVictory(wolfsToWin);
        WolfAndSheepsGame wolfAndSheepsGame = new WolfAndSheepsGame(board, victory);

        BoardMarshaller boardMarshaller = new BoardMarshaller(board.getPositionSet().getMaxSingleNumber());
        LineMarshaller lineMarshaller = new LineMarshaller(board.getPositionSet().getMaxSingleNumber());
        Long encodedBoard = boardMarshaller.marshall(board);

        Optional<Long> lineAsLong = linesRedisStorage.get(wolfAndSheepsGame.getGameId() + "_sample", encodedBoard);
        if (lineAsLong.isPresent()) {
            Line line = lineMarshaller.unmarshall(lineAsLong.get(), board);
            LOGGER.info("Winning color = " + line.winningColor());
            return null;
        }

        Function<TwoDimensionalBoard, Boolean> toSave = (twoDimensionalBoard) -> {
            int currentSum = twoDimensionalBoard.getWhitePieces().stream()
                    .map(TwoDimensionalPiece::getCurrentPosition)
                    .map(TwoDimensionalAvailablePositionSet.TwoDimensionalPosition::y)
                    .reduce(0, (a, b) -> a + b);
            return ((currentSum - initialSum) % 4 == 0) && (twoDimensionalBoard.getNextToMove() == initialColor);
        };

        if (cache == null) {
            cache = new ModifiableRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId() + "_sample");
        }
        BruteForce bruteForce = new BruteForce(board, victory, cache, toSave);
        Line line = bruteForce.analisePositionParallel(board);
        LOGGER.info("Winning color = " + line.winningColor());
        return cache;
    }
}
