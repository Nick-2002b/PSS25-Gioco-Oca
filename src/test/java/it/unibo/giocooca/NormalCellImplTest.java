package it.unibo.giocooca;

import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.impl.NormalCellImpl;
import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NormalCellImplTest {
    private NormalCellImpl cell;

    @BeforeEach
    void setup(){
        cell = new NormalCellImpl(10);
    }

    @Test
    void typeShouldBeNormal() {
        Assertions.assertEquals(CellType.NORMAL, cell.getType());
    }

    @Test
    void positionShouldMatch() {
        Assertions.assertEquals(10, cell.getPosition());
    }

    @Test
    void applyEffectShouldNotChangePlayerPosition() {
        final PlayerImpl player = new PlayerImpl("Mario", new PieceImpl("dog", "red"));
        player.setPosition(10);
        cell.applyEffect(player);

        Assertions.assertEquals(10, player.getPosition());
    }

    @Test
    void applyEffectShouldNotImprisonPlayer() {
        final PlayerImpl player = new PlayerImpl("Mario", new PieceImpl("dog", "red"));

        cell.applyEffect(player);

        Assertions.assertFalse(player.isInPrison());
    }
}
