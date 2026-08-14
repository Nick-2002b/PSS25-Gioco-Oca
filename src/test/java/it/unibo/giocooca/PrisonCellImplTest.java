package it.unibo.giocooca;

import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;
import it.unibo.giocooca.model.impl.PrisonCellImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrisonCellImplTest {
    private PrisonCellImpl cell;

    @BeforeEach
    void setup() {
        cell = new PrisonCellImpl(32);
    }

    @Test
    void typeShouldBePrison() {
        Assertions.assertEquals(CellType.PRISON, cell.getType());
    }

    @Test
    void applyEffectShouldImprisonPlayer() {
        final PlayerImpl player = new PlayerImpl("Mario", new PieceImpl("dog", "red"));

        cell.applyEffect(player);

        Assertions.assertTrue(player.isInPrison());
    }
}
