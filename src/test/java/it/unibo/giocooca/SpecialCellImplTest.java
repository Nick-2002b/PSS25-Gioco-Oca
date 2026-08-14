package it.unibo.giocooca;

import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;
import it.unibo.giocooca.model.impl.SpecialCellImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpecialCellImplTest {

    @Test
    void typeShouldBePrison() {
        final SpecialCellImpl cell = new SpecialCellImpl(10, 3, 63);
        Assertions.assertEquals(CellType.SPECIAL, cell.getType());
    }

    @Test
    void positiveOffsetShouldMovePlayerForward() {
        final PlayerImpl player = new PlayerImpl("Mario", new PieceImpl("dog", "red"));
        player.setPosition(10);
        final SpecialCellImpl cell = new SpecialCellImpl(10, 3, 63);

        cell.applyEffect(player);

        Assertions.assertEquals(13, player.getPosition());
    }

    @Test
    void negativeOffsetShouldMovePlayerBackward() {
        final PlayerImpl player = new PlayerImpl("Mario", new PieceImpl("dog", "red"));
        player.setPosition(20);
        final SpecialCellImpl cell = new SpecialCellImpl(20, -5, 63);

        cell.applyEffect(player);

        Assertions.assertEquals(15, player.getPosition());
    }

    @Test
    void effectPassingEndShouldSetPlayerOnTheLastCell() {
        final PlayerImpl player = new PlayerImpl("Mario", new PieceImpl("dog", "red"));
        player.setPosition(60);
        final SpecialCellImpl cell = new SpecialCellImpl(60, 6, 63);

        cell.applyEffect(player);

        Assertions.assertEquals(63, player.getPosition());
    }

    @Test
    void effectReachingBeforeStartShouldSetPlayerOnTheFirstCell() {
        final PlayerImpl player = new PlayerImpl("Mario", new PieceImpl("dog", "red"));
        player.setPosition(5);
        final SpecialCellImpl cell = new SpecialCellImpl(5, -6, 63);

        cell.applyEffect(player);

        Assertions.assertEquals(1, player.getPosition());
    }
}
