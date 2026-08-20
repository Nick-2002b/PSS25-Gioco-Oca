package it.unibo.giocooca;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Dice;
import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.impl.BoardImpl;
import it.unibo.giocooca.model.impl.DiceImpl;
import it.unibo.giocooca.model.impl.MatchImpl;
import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;

public class MatchImplTest {
    private static final int BOARD_SIZE = 63;
    private static final int SPECIAL_CELLS_COUNT = 6;
    private static final long SEED = 42L;

    private Player player1;
    private Player player2;
    private GameConfig config;
    private Board board;
    private Dice dice;
    private Match match;
    
    @BeforeEach
    void setUP(){
        this.player1 = new PlayerImpl("Pippo", new PieceImpl("Mucca", "rosso"));           
        this.player2 = new PlayerImpl("Pluto", new PieceImpl("Cane", "verde"));

        this.config = new GameConfig(BOARD_SIZE, SPECIAL_CELLS_COUNT, SEED);
        this.board = new BoardImpl(this.config);
        this.dice = new DiceImpl();
        this.match = new MatchImpl(List.of(this.player1, this.player2), this.board, this.dice);

    }

    @Test
    void testMatchInit(){
        assertAll("Test situazione inizio partita",
            () -> assertEquals(player1, match.getCurrentPlayer()),
            () -> assertEquals("Pippo", match.getCurrentPlayer().getNickName()),
            () -> assertFalse(match.isGameOver()),
            () -> assertNull(match.getWinner()),
            () -> assertEquals(2, match.getPlayers().size()),
            () -> assertEquals(0, player1.getPosition()),
            () -> assertEquals(0, player2.getPosition())
        );
    }
    @Test
    void testTurn(){
        assertEquals(player1, match.getCurrentPlayer());
        this.match.nextTurn();
        assertEquals(player2, match.getCurrentPlayer());
        this.match.nextTurn();
        assertEquals(player1, match.getCurrentPlayer());
    }
    @Test
    void testApplySpecialCell() {
        Cell specialCell = null;
        for (Cell cell : this.board.getAllCells()) {
            if (cell.getType() == CellType.SPECIAL) {
                specialCell = cell;
                break;
            }
        }
        assertNotNull(specialCell, "Deve esserci almeno una casella speciale");

        int specialPosition = specialCell.getPosition();
        int beforePosPlayer = this.match.getCurrentPlayer().getPosition();
        this.match.moveCurrentPlayer(specialPosition);
        int afterPosPlayer = this.match.getCurrentPlayer().getPosition();
        assertNotEquals(beforePosPlayer, afterPosPlayer);
    }
}
