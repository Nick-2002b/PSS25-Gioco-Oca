package it.unibo.giocooca;

import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test per il giocatore.
 */
final class PlayerImplTest {
    private static final int NEW_POSITION = 15;

    private PlayerImpl player;

    @BeforeEach
    void setUp() {
        final PieceImpl piece = new PieceImpl("dog", "red");
        player = new PlayerImpl("Mario", piece);
    }

    @Test
    void initalPositionShouldBeZero() {
        Assertions.assertEquals(0, player.getPosition());
    }

    @Test
    void setPositionShouldUpdatePosition() {
        player.setPosition(NEW_POSITION);
        Assertions.assertEquals(NEW_POSITION, player.getPosition());
    }

    @Test
    void initiallyPlayerShouldNotBeInPrison() {
        Assertions.assertFalse(player.isInPrison());
    }

    @Test
    void setInPrisonTrueShouldImprisonPlayer() {
        player.setInPrison(true);
        Assertions.assertTrue(player.isInPrison());
    }

    @Test
    void setInPrisonFalseShouldFreePlayer() {
        player.setInPrison(true);
        player.setInPrison(false);
        Assertions.assertFalse(player.isInPrison());
    }

    @Test
    void pieceShouldReturnCorrectPiece() {
        Assertions.assertNotNull(player.getPiece());
        Assertions.assertEquals("dog", player.getPiece().getName());
        Assertions.assertEquals("red", player.getPiece().getColor());
    }
}
