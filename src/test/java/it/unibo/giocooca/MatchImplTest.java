package it.unibo.giocooca;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import it.unibo.giocooca.model.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Dice;
import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.Player;

/**
 * Test per la partita.
 */
final class MatchImplTest {
    private static final int BOARD_SIZE = 63;
    private static final int SPECIAL_CELLS_COUNT = 6;
    private static final long SEED = 42L;

    private Player player1;
    private Player player2;
    private Board board;
    private Match match;

    @BeforeEach
    void setUp() {
        this.player1 = new PlayerImpl("Pippo", new PieceImpl("Mucca", "rosso"));
        this.player2 = new PlayerImpl("Pluto", new PieceImpl("Cane", "verde"));

        final GameConfig config = new GameConfig(BOARD_SIZE, SPECIAL_CELLS_COUNT, SEED, new RandomPlacementStrategy());
        this.board = new BoardImpl(config);
        final Dice dice = new DiceImpl();
        this.match = new MatchImpl(List.of(this.player1, this.player2), this.board, dice);

    }

    @Test
    void testMatchInit() {
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
    void testTurn() {
        assertEquals(player1, match.getCurrentPlayer());
        this.match.nextTurn();
        assertEquals(player2, match.getCurrentPlayer());
        this.match.nextTurn();
        assertEquals(player1, match.getCurrentPlayer());
    }

    @Test
    void testApplySpecialCell() {
        Cell specialCell = null;
        for (final Cell cell : this.board.getAllCells()) {
            if (cell.getType() == CellType.SPECIAL) {
                specialCell = cell;
                break;
            }
        }
        assertNotNull(specialCell, "Deve esserci almeno una casella speciale");

        final int specialPosition = specialCell.getPosition();
        final int beforePosPlayer = this.match.getCurrentPlayer().getPosition();
        this.match.moveCurrentPlayer(specialPosition);
        final int afterPosPlayer = this.match.getCurrentPlayer().getPosition();
        assertNotEquals(beforePosPlayer, afterPosPlayer);
    }

    @Test
    void testChainedSpecialCells() {
        final Cell[] cells = new Cell[64];
        cells[0] = new StartCellImpl();
        for (int i = 1; i < 64; i++) {
            cells[i] = new NormalCellImpl(i);
        }
        cells[10] = new SpecialCellImpl(10, 4, 63);
        cells[14] = new SpecialCellImpl(14, 3, 63);
        final Board customBoard = new Board() {
            @Override
            public int getSize() { return 64; }
            @Override
            public Cell getCell(int pos) { return cells[pos]; }
            @Override
            public List<Cell> getAllCells() { return List.of(cells); }
        };
        final Match customMatch = new MatchImpl(List.of(player1, player2), customBoard, new DiceImpl());
        customMatch.moveCurrentPlayer(10);
        assertEquals(17, player1.getPosition());
        assertEquals(List.of(10, 14, 17), customMatch.getLastMovePositions());
    }
}
