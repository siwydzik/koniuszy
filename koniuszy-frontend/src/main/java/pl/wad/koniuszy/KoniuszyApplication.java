package pl.wad.koniuszy;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
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
import pl.wad.game.wolfandsheeps.engine.marshall.BoardMarshaller;
import pl.wad.game.wolfandsheeps.engine.positioncache.InMemoryPositionCache;
import pl.wad.koniuszy.redis.LinesRedisStorage;
import pl.wad.koniuszy.redis.ModifiableRedisPositionCache;
import pl.wad.koniuszy.redis.ReadOnlyRedisPositionCache;
import pl.wad.koniuszy.redis.RedisConfig;

import java.util.function.Function;

@SpringBootApplication
@Import(RedisConfig.class)
public class KoniuszyApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(KoniuszyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KoniuszyApplication.class, args);
    }

    @Autowired
    private LinesRedisStorage linesRedisStorage;

//    @PostConstruct
//    public void init() {
//
//        LOGGER.info("init");
//
//        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
//                .withFirstMove(Color.BLACK)
//                .withPiece(new Wolf(), 5, 7)
//                .withPiece(new Sheep(), 0, 0)
//                .withPiece(new Sheep(), 2, 0)
//                .withPiece(new Sheep(), 4, 0)
//                .withPiece(new Sheep(), 6, 0)
//                .build();
//
//        WoolfAndSheepsVictory victory = new WoolfAndSheepsVictory();
//        WolfAndSheepsGame wolfAndSheepsGame = new WolfAndSheepsGame(board, victory);
//        // BruteForce bruteForce = new BruteForce(board, new WoolfAndSheepsVictory(), new ModifiableRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId()));
//        BruteForce bruteForce = new BruteForce(board, new WoolfAndSheepsVictory(), new InMemoryPositionCache());
//        bruteForce.analisePositionParallel(board);
//
//    }

//    @PostConstruct
//    public void init() {
//
//        LOGGER.info("init");
//
//        final TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
//                .withFirstMove(Color.WHITE)
//                .withPiece(new Wolf(), 3, 7)
//                .withPiece(new Wolf(), 5, 7)
//                .withPiece(new Sheep(), 0, 0)
//                .withPiece(new Sheep(), 2, 0)
//                .withPiece(new Sheep(), 4, 0)
//                .withPiece(new Sheep(), 6, 0)
//                .withPiece(new Sheep(), 1, 1)
//                .build();
//
//        final int initialSum = board.getWhitePieces().stream()
//                .map(TwoDimensionalPiece::getCurrentPosition)
//                .map(TwoDimensionalAvailablePositionSet.TwoDimensionalPosition::y)
//                .reduce(0, (a, b) -> a + b);
//        Color initialColor = board.getNextToMove();
//
//        WoolfAndSheepsVictory victory = new WoolfAndSheepsVictory();
//        WolfAndSheepsGame wolfAndSheepsGame = new WolfAndSheepsGame(board, victory);
//
//        BoardMarshaller marshaller = new BoardMarshaller(board.getPositionSet().getMaxSingleNumber());
//        Function<Long, Boolean> matches = (position) -> {
//            TwoDimensionalBoard twoDimensionalBoard = marshaller.unmarshall(position, board);
//            int currentSum = twoDimensionalBoard.getWhitePieces().stream()
//                    .map(TwoDimensionalPiece::getCurrentPosition)
//                    .map(TwoDimensionalAvailablePositionSet.TwoDimensionalPosition::y)
//                    .reduce(0, (a, b) -> a + b);
//            return ((currentSum - initialSum) % 8 == 0) && (twoDimensionalBoard.getNextToMove() == initialColor);
//        };
//
//        linesRedisStorage.createSample(wolfAndSheepsGame.getGameId(), wolfAndSheepsGame.getGameId() + "_sample", matches);
//
//    }

//    @PostConstruct
//    public void init() {
//
//        LOGGER.info("init");
//
//        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
//                .withFirstMove(Color.BLACK)
//                .withPiece(new Wolf(), 3, 7)
//                .withPiece(new Wolf(), 5, 7)
//                .withPiece(new Sheep(), 0, 0)
//                .withPiece(new Sheep(), 2, 0)
//                .withPiece(new Sheep(), 4, 0)
//                .withPiece(new Sheep(), 6, 0)
//                .withPiece(new Sheep(), 1, 1)
//                .build();
//
//        WoolfAndSheepsVictory victory = new WoolfAndSheepsVictory();
//        WolfAndSheepsGame wolfAndSheepsGame = new WolfAndSheepsGame(board, victory);
//        //BruteForce bruteForce = new BruteForce(board, new WoolfAndSheepsVictory(), new ModifiableRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId()));
//        BruteForce bruteForce = new BruteForce(board, new WoolfAndSheepsVictory(), new ReadOnlyRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId() + "_sample"));
//        bruteForce.analisePosition(board);
//
//    }

}
