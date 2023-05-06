package pl.wad.game.wolfandsheeps

import pl.wad.game.Color
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder.InitialPosition
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet
import spock.lang.Specification

class WolfSpec extends Specification {

    def "should not be able to move on 1x1 board"() {
        given:
        Wolf wolf = getWolfOnBoard(1, 0, 0);
        when:
        boolean canMove = wolf.canMove()
        then:
        canMove == false
    }

    def "should every move be invalid on 1x1 board"() {
        given:
        Wolf wolf = getWolfOnBoard(1, 0, 0);
        expect:
        wolf.getBoard().getPositionSet().getValidPosition(x, y) == position
        where:
        x  | y  | position
        1  | 1  | null
        1  | -1 | null
        -1 | 1  | null
        -1 | -1 | null
    }

    def "should be able to move on 3x3 board in every direction if placed in the center"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1);
        expect:
        wolf.isMoveAllowed(wolf.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | true
        0 | 2 | true
        2 | 0 | true
        2 | 2 | true
    }

    def "should be able to move on 3x3 board only to left top"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 0), new InitialPosition(2, 0), new InitialPosition(2, 2));
        expect:
        wolf.isMoveAllowed(wolf.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | false
        0 | 2 | true
        2 | 0 | false
        2 | 2 | false
    }

    def "should be able to move on 3x3 board only to left bottom"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(2, 0), new InitialPosition(2, 2));
        expect:
        wolf.isMoveAllowed(wolf.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | true
        0 | 2 | false
        2 | 0 | false
        2 | 2 | false
    }

    def "should be able to move on 3x3 board only to right top"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(2, 0), new InitialPosition(0, 0));
        expect:
        wolf.isMoveAllowed(wolf.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | false
        0 | 2 | false
        2 | 0 | false
        2 | 2 | true
    }

    def "should be able to move on 3x3 board only to right bottom"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 0), new InitialPosition(0, 2), new InitialPosition(2, 2));
        expect:
        wolf.isMoveAllowed(wolf.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | false
        0 | 2 | false
        2 | 0 | true
        2 | 2 | false
    }

    def "should be moved to left top"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 0), new InitialPosition(2, 0), new InitialPosition(2, 2));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = wolf.getPossibleMoves()
        then:
        list.size() == 1
        list.get(0).x() == 0
        list.get(0).y() == 2
        when:
        wolf.moveToPosition(wolf.getBoard().getPositionSet().getValidPosition(0, 2))
        then:
        wolf.getCurrentPosition() == wolf.getBoard().getPositionSet().getValidPosition(0, 2)
    }

    def "should be moved to left bottom"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(2, 0), new InitialPosition(2, 2));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = wolf.getPossibleMoves()
        then:
        list.size() == 1
        list.get(0).x() == 0
        list.get(0).y() == 0
        when:
        wolf.moveToPosition(wolf.getBoard().getPositionSet().getValidPosition(0, 0))
        then:
        wolf.getCurrentPosition() == wolf.getBoard().getPositionSet().getValidPosition(0, 0)
    }

    def "should be moved to right top"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(2, 0), new InitialPosition(0, 0));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = wolf.getPossibleMoves()
        then:
        list.size() == 1
        list.get(0).x() == 2
        list.get(0).y() == 2
        when:
        wolf.moveToPosition(wolf.getBoard().getPositionSet().getValidPosition(2, 2))
        then:
        wolf.getCurrentPosition() == wolf.getBoard().getPositionSet().getValidPosition(2, 2)
    }

    def "should be moved to right bottom"() {
        given:
        Wolf wolf = getWolfOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(0, 0), new InitialPosition(2, 2));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = wolf.getPossibleMoves()
        then:
        list.size() == 1
        list.get(0).x() == 2
        list.get(0).y() == 0
        when:
        wolf.moveToPosition(wolf.getBoard().getPositionSet().getValidPosition(2, 0))
        then:
        wolf.getCurrentPosition() == wolf.getBoard().getPositionSet().getValidPosition(2, 0)
    }

    private Wolf getWolfOnBoard(int d, int x, int y, InitialPosition... sheepPositions) {
        Wolf wolf;
        TwoDimensionalBoardBuilder builder = new TwoDimensionalBoardBuilder(d, d)
                .withFirstMove(Color.BLACK)
                .withPiece(wolf = new Wolf(), x, y)
        if (sheepPositions != null) {
            Arrays.asList(sheepPositions).stream().forEach { it -> builder.withPiece(new Sheep(), it.x(), it.y()) }
        }
        builder.build()
        return wolf
    }
}
