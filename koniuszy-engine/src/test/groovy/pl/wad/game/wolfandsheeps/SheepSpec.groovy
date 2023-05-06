package pl.wad.game.wolfandsheeps

import pl.wad.game.Color
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder
import pl.wad.game.twodimensional.TwoDimensionalBoardBuilder.InitialPosition
import pl.wad.game.twodimensional.TwoDimensionalAvailablePositionSet
import spock.lang.Specification

class SheepSpec extends Specification {

    def "should not be able to move on 1x1 board"() {
        given:
        Sheep sheep = getSheepOnBoard(1, 0, 0);
        when:
        boolean canMove = sheep.canMove()
        then:
        canMove == false
    }

    def "should every move be invalid on 1x1 board"() {
        given:
        Sheep sheep = getSheepOnBoard(1, 0, 0);
        expect:
        sheep.getBoard().getPositionSet().getValidPosition(x, y) == position
        where:
        x  | y  | position
        1  | 1  | null
        1  | -1 | null
        -1 | 1  | null
        -1 | -1 | null
    }

    def "should be able to move forward on 3x3 if placed in the center"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1);
        expect:
        sheep.isMoveAllowed(sheep.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | false
        0 | 2 | true
        2 | 0 | false
        2 | 2 | true
    }

    def "should be able to move on 3x3 board only to left top"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1, new InitialPosition(2, 2));
        expect:
        sheep.isMoveAllowed(sheep.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | false
        0 | 2 | true
        2 | 0 | false
        2 | 2 | false
    }

    def "should be able to move on 3x3 board only to right top"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1, new InitialPosition(0, 2));
        expect:
        sheep.isMoveAllowed(sheep.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | false
        0 | 2 | false
        2 | 0 | false
        2 | 2 | true
    }

    def "should not be able to move forward on 3x3"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(2, 2));
        expect:
        sheep.isMoveAllowed(sheep.getBoard().getPositionSet().getValidPosition(x, y)) == can
        where:
        x | y | can
        0 | 0 | false
        0 | 2 | false
        2 | 0 | false
        2 | 2 | false
    }

    def "should be moved to left top"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1, new InitialPosition(0, 0), new InitialPosition(2, 0), new InitialPosition(2, 2));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = sheep.getPossibleMoves()
        then:
        list.size() == 1
        list.get(0).x() == 0
        list.get(0).y() == 2
        when:
        sheep.moveToPosition(sheep.getBoard().getPositionSet().getValidPosition(0, 2))
        then:
        sheep.getCurrentPosition() == sheep.getBoard().getPositionSet().getValidPosition(0, 2)
    }

    def "should not be moved to left bottom"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(2, 0), new InitialPosition(2, 2));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = sheep.getPossibleMoves()
        then:
        list.size() == 0
    }

    def "should be moved to right top"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(2, 0), new InitialPosition(0, 0));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = sheep.getPossibleMoves()
        then:
        list.size() == 1
        list.get(0).x() == 2
        list.get(0).y() == 2
        when:
        sheep.moveToPosition(sheep.getBoard().getPositionSet().getValidPosition(2, 2))
        then:
        sheep.getCurrentPosition() == sheep.getBoard().getPositionSet().getValidPosition(2, 2)
    }

    def "should not be moved to right bottom"() {
        given:
        Sheep sheep = getSheepOnBoard(3, 1, 1, new InitialPosition(0, 2), new InitialPosition(0, 0), new InitialPosition(2, 2));
        when:
        List<TwoDimensionalAvailablePositionSet.TwoDimensionalPosition> list = sheep.getPossibleMoves()
        then:
        list.size() == 0
    }

    private Sheep getSheepOnBoard(int d, int x, int y, InitialPosition... wolfPositions) {
        Sheep sheep;
        TwoDimensionalBoardBuilder builder = new TwoDimensionalBoardBuilder(d, d)
                .withFirstMove(Color.WHITE)
                .withPiece(sheep = new Sheep(), x, y)
        if (wolfPositions != null) {
            Arrays.asList(wolfPositions).stream().forEach { it -> builder.withPiece(new Wolf(), it.x(), it.y()) }
        }
        builder.build()
        return sheep
    }
}
