package pl.wad.koniuszy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wad.game.Color;
import pl.wad.game.twodimensional.TwoDimensionalBoard;
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder;
import pl.wad.game.wolfandsheeps.Sheep;
import pl.wad.game.wolfandsheeps.Wolf;
import pl.wad.game.wolfandsheeps.WolfAndSheepsGame;
import pl.wad.game.wolfandsheeps.WoolfAndSheepsVictory;
import pl.wad.game.wolfandsheeps.engine.BruteForce;
import pl.wad.game.wolfandsheeps.engine.Engine;
import pl.wad.game.wolfandsheeps.engine.Line;
import pl.wad.game.wolfandsheeps.engine.Table;
import pl.wad.koniuszy.redis.LinesRedisStorage;
import pl.wad.koniuszy.redis.ReadOnlyRedisPositionCache;

@RestController
public class KoniuszyController {

    private volatile WolfAndSheepsGame wolfAndSheepsGame;
    //private volatile Engine engine;

    @Autowired
    private LinesRedisStorage linesRedisStorage;


    @PostMapping("/koniuszy/reset")
    public State reset() {

        WoolfAndSheepsVictory victory = new WoolfAndSheepsVictory();

        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.WHITE)
                .withPiece(new Wolf(), 3, 7)
                .withPiece(new Wolf(), 5, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .withPiece(new Sheep(), 1, 1)
                .withPiece(new Sheep(), 7, 1)
                .build();

        wolfAndSheepsGame = new WolfAndSheepsGame(board, victory);
        //engine = new Table(board.getPositionSet(), new ReadOnlyRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId()));
        //Engine engine = new BruteForce(wolfAndSheepsGame, new ReadOnlyRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId() + "_sample"));
        Engine engine = new BruteForce(wolfAndSheepsGame, new ReadOnlyRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId() + "_sample"));
        Line bestLine = wolfAndSheepsGame.getBestLine(engine);
        return new State(wolfAndSheepsGame.getPosition(), null, bestLine.winningColor(), bestLine.count());
    }

    @GetMapping("/koniuszy/position")
    public State position() {
        return new State(wolfAndSheepsGame.getPosition(), null, null, null);
    }

    @PostMapping("/koniuszy/moveAndAnaliseLine")
    public State moveAndAnaliseLine(@RequestParam String from, @RequestParam String to) {
        wolfAndSheepsGame.move(from, to);
        Engine engine = new BruteForce(wolfAndSheepsGame, new ReadOnlyRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId() + "_sample"));
        Line line = wolfAndSheepsGame.getBestLine(engine);
        return new State(wolfAndSheepsGame.getPosition(), wolfAndSheepsGame.victoryCheck(), line.winningColor(), line.count());
    }

    @PostMapping("/koniuszy/move")
    public boolean move(@RequestParam String from, @RequestParam String to) {
        return wolfAndSheepsGame.move(from, to);
    }

    @PostMapping("/koniuszy/cpuMove")
    public State cpuMove() {
        Engine engine = new BruteForce(wolfAndSheepsGame, new ReadOnlyRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId() + "_sample"));
        wolfAndSheepsGame.cpuMove(engine);
        Line line = wolfAndSheepsGame.getBestLine(engine);
        return new State(wolfAndSheepsGame.getPosition(), wolfAndSheepsGame.victoryCheck(), line.winningColor(), line.count());
    }

    @PostMapping("/koniuszy/hint")
    public Hint hint() {
        Engine engine = new BruteForce(wolfAndSheepsGame, new ReadOnlyRedisPositionCache(linesRedisStorage, wolfAndSheepsGame.getGameId() + "_sample"));
        return new Hint(
                wolfAndSheepsGame.getBestLine(engine).nextMove().from().getClassicNotation(),
                wolfAndSheepsGame.getBestLine(engine).nextMove().to().getClassicNotation()
        );
    }

}
