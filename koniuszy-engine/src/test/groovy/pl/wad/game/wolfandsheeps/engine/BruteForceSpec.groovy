package pl.wad.game.wolfandsheeps.engine

import pl.wad.game.Color
import pl.wad.game.twodimensional.TwoDimensionalBoard
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder
import pl.wad.game.wolfandsheeps.Sheep
import pl.wad.game.wolfandsheeps.Wolf
import pl.wad.game.wolfandsheeps.WoolfAndSheepsVictory
import spock.lang.Specification

class BruteForceSpec extends Specification {

    def "ending 1"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.WHITE)
                .withPiece(new Wolf(), 2, 6)
                .withPiece(new Sheep(), 1, 5)
                .withPiece(new Sheep(), 3, 5)
                .withPiece(new Sheep(), 4, 6)
                .withPiece(new Sheep(), 5, 7)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.nextMove().from().x() == 4
        optimalChoice.nextMove().from().y() == 6
        optimalChoice.nextMove().to().x() == 3
        optimalChoice.nextMove().to().y() == 7
        optimalChoice.winningColor() == Color.WHITE
    }

    def "ending 2"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.WHITE)
                .withPiece(new Wolf(), 1, 7)
                .withPiece(new Sheep(), 1, 5)
                .withPiece(new Sheep(), 3, 5)
                .withPiece(new Sheep(), 3, 7)
                .withPiece(new Sheep(), 5, 7)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.nextMove().from().x() == 3
        optimalChoice.nextMove().from().y() == 5
        optimalChoice.nextMove().to().x() == 2
        optimalChoice.nextMove().to().y() == 6
        optimalChoice.winningColor() == Color.WHITE
    }

    def "ending 3"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.WHITE)
                .withPiece(new Wolf(), 1, 7)
                .withPiece(new Sheep(), 0, 6)
                .withPiece(new Sheep(), 3, 5)
                .withPiece(new Sheep(), 3, 7)
                .withPiece(new Sheep(), 6, 6)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.nextMove().from().x() == 3
        optimalChoice.nextMove().from().y() == 5
        optimalChoice.nextMove().to().x() == 2
        optimalChoice.nextMove().to().y() == 6
        optimalChoice.winningColor() == Color.WHITE
    }


    def "ending 4"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.WHITE)
                .withPiece(new Wolf(), 1, 7)
                .withPiece(new Sheep(), 0, 6)
                .withPiece(new Sheep(), 3, 5)
                .withPiece(new Sheep(), 3, 7)
                .withPiece(new Sheep(), 5, 7)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.nextMove().from().x() == 3
        optimalChoice.nextMove().from().y() == 5
        optimalChoice.nextMove().to().x() == 2
        optimalChoice.nextMove().to().y() == 6
        optimalChoice.winningColor() == Color.WHITE
    }

    def "ending 5"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.BLACK)
                .withPiece(new Wolf(), 1, 7)
                .withPiece(new Sheep(), 0, 6)
                .withPiece(new Sheep(), 4, 6)
                .withPiece(new Sheep(), 3, 7)
                .withPiece(new Sheep(), 5, 7)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.nextMove().from().x() == 1
        optimalChoice.nextMove().from().y() == 7
        optimalChoice.nextMove().to().x() == 2
        optimalChoice.nextMove().to().y() == 6
        optimalChoice.winningColor() == Color.BLACK
    }


    def "lupus 1"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.BLACK)
                .withPiece(new Wolf(), 5, 3)
                .withPiece(new Sheep(), 1, 3)
                .withPiece(new Sheep(), 3, 3)
                .withPiece(new Sheep(), 4, 2)
                .withPiece(new Sheep(), 6, 2)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.winningColor() == Color.WHITE
    }

    def "lupus 2"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.WHITE)
                .withPiece(new Wolf(), 5, 3)
                .withPiece(new Sheep(), 1, 3)
                .withPiece(new Sheep(), 3, 3)
                .withPiece(new Sheep(), 4, 2)
                .withPiece(new Sheep(), 6, 2)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.winningColor() == Color.WHITE
    }

    def "classic black starts"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.BLACK)
                .withPiece(new Wolf(), 3, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.winningColor() == Color.WHITE
    }

    def "classic white starts"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.WHITE)
                .withPiece(new Wolf(), 3, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.winningColor() == Color.WHITE
    }

    def "two wolfs"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.BLACK)
                .withPiece(new Wolf(), 3, 7)
                .withPiece(new Wolf(), 5, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .build();

        when:
        BruteForce bruteForce = new BruteForce(board, new WoolfAndSheepsVictory());
        Line optimalChoice = bruteForce.analisePositionParallel(board);
        then:
        optimalChoice.winningColor() == Color.BLACK
    }

    def "two wolfs five sheeps"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.BLACK)
                .withPiece(new Wolf(), 3, 7)
                .withPiece(new Wolf(), 5, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .withPiece(new Sheep(), 1, 1)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.winningColor() == Color.BLACK
    }

    def "two wolfs six sheeps"() {
        given:
        TwoDimensionalBoard board = new TwoDimensionalBoardBuilder(8, 8)
                .withFirstMove(Color.BLACK)
                .withPiece(new Wolf(), 3, 7)
                .withPiece(new Wolf(), 5, 7)
                .withPiece(new Sheep(), 0, 0)
                .withPiece(new Sheep(), 2, 0)
                .withPiece(new Sheep(), 4, 0)
                .withPiece(new Sheep(), 6, 0)
                .withPiece(new Sheep(), 1, 1)
                .withPiece(new Sheep(), 7, 1)
                .build();

        when:
        Line optimalChoice = new BruteForce(board, new WoolfAndSheepsVictory()).analisePositionParallel(board);
        then:
        optimalChoice.winningColor() == Color.WHITE
    }

}
