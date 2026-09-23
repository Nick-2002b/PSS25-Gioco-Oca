package it.unibo.giocooca;

import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;
import it.unibo.giocooca.model.impl.SpecialCellImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test della casella speciale.
 */
class SpecialCellImplTest {

    private static final String PLAYER_NAME = "Mario";
    private static final String PIECE_NAME = "dog";
    private static final String PIECE_COLOR = "red";
    private static final int BOARD_SIZE = 63;
    private static final int POSITION_AFTER_FORWARD_OFFSET = 13;
    private static final int STARTING_POSITION_FOR_BACKWARD_OFFSET = 20;
    private static final int POSITION_AFTER_BACKWARD_OFFSET = 15;
    private static final int STARTING_POSITION_NEAR_END = 60;
    private static final int STARTING_POSITION_NEAR_START = 5;

    @Test
    void typeShouldBeSpecial() {
        final SpecialCellImpl cell = new SpecialCellImpl(10, 3, BOARD_SIZE);
        Assertions.assertEquals(CellType.SPECIAL, cell.getType());
    }

    @Test
    void positiveOffsetShouldMovePlayerForward() {
        final PlayerImpl player = new PlayerImpl(PLAYER_NAME, new PieceImpl(PIECE_NAME, PIECE_COLOR));
        player.setPosition(10);
        final SpecialCellImpl cell = new SpecialCellImpl(10, 3, BOARD_SIZE);

        cell.applyEffect(player);

        Assertions.assertEquals(POSITION_AFTER_FORWARD_OFFSET, player.getPosition());
    }

    @Test
    void negativeOffsetShouldMovePlayerBackward() {
        final PlayerImpl player = new PlayerImpl(PLAYER_NAME, new PieceImpl(PIECE_NAME, PIECE_COLOR));
        player.setPosition(STARTING_POSITION_FOR_BACKWARD_OFFSET);
        final SpecialCellImpl cell = new SpecialCellImpl(STARTING_POSITION_FOR_BACKWARD_OFFSET, -5, BOARD_SIZE);

        cell.applyEffect(player);

        Assertions.assertEquals(POSITION_AFTER_BACKWARD_OFFSET, player.getPosition());
    }

    @Test
    void effectPassingEndShouldSetPlayerOnTheLastCell() {
        final PlayerImpl player = new PlayerImpl(PLAYER_NAME, new PieceImpl(PIECE_NAME, PIECE_COLOR));
        player.setPosition(STARTING_POSITION_NEAR_END);
        final SpecialCellImpl cell = new SpecialCellImpl(STARTING_POSITION_NEAR_END, 6, BOARD_SIZE);

        cell.applyEffect(player);

        Assertions.assertEquals(BOARD_SIZE, player.getPosition());
    }

    @Test
    void effectReachingBeforeStartShouldSetPlayerOnTheFirstCell() {
        final PlayerImpl player = new PlayerImpl(PLAYER_NAME, new PieceImpl(PIECE_NAME, PIECE_COLOR));
        player.setPosition(STARTING_POSITION_NEAR_START);
        final SpecialCellImpl cell = new SpecialCellImpl(STARTING_POSITION_NEAR_START, -6, BOARD_SIZE);

        cell.applyEffect(player);

        Assertions.assertEquals(1, player.getPosition());
    }
}
