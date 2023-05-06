package pl.wad.koniuszy


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.wad.game.Color
import pl.wad.game.twodimensional.TwoDimensionalBoard
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder
import pl.wad.game.wolfandsheeps.Sheep
import pl.wad.game.wolfandsheeps.Wolf
import pl.wad.game.wolfandsheeps.WoolfAndSheepsVictory
import pl.wad.game.wolfandsheeps.engine.Line

import pl.wad.koniuszy.redis.LinesRedisStorage
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest
@Ignore
class RedisBackedBruteForceSpec extends Specification {

    @Autowired
    private LinesRedisStorage linesRedisStorage;

    def "two wolfs"() {
//        given:
//        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
//                .withFirstMove(Color.BLACK)
//                .withPiece(new Wolf(), 3, 7)
//                .withPiece(new Wolf(), 5, 7)
//                .withPiece(new Sheep(), 0, 0)
//                .withPiece(new Sheep(), 2, 0)
//                .withPiece(new Sheep(), 4, 0)
//                .withPiece(new Sheep(), 6, 0)
//                .build();
//
//        when:
//        RedisBackedBruteForce bruteForce = new RedisBackedBruteForce(board, new WoolfAndSheepsVictory(), linesRedisStorage);
//        Line optimalChoice = bruteForce.analisePositionParallel(board);
//        then:
//        optimalChoice.winningColor() == Color.BLACK
    }


}
