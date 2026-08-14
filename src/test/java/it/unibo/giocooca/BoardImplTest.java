package it.unibo.giocooca;

import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.impl.BoardImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BoardImplTest {
    private static final long SEED = 42L;
    private static final int NUM_SPECIAL = 10;

    private BoardImpl board;

    @BeforeEach
    void setup() {
        final GameConfig config = new GameConfig(63, NUM_SPECIAL, SEED);
        board = new BoardImpl(config);
    }

    @Test
    void boardShouldHave63Cells() {
        Assertions.assertEquals(63, board.getSize());
    }

    @Test
    void cell1ShouldBeNormal() {
        Assertions.assertEquals(CellType.NORMAL, board.getCell(1).getType());
    }

    @Test
    void cell32ShouldBePrison() {
        Assertions.assertEquals(CellType.PRISON, board.getCell(32).getType());
    }

    @Test
    void cell63ShouldBeNormal() {
        Assertions.assertEquals(CellType.NORMAL, board.getCell(63).getType());
    }

    @Test
    void numberOfSpecialCellsShouldMatchConfig() {
        final long specialCount = board.getAllCells().stream()
                .filter(x -> x.getType() == CellType.SPECIAL)
                .count();

        Assertions.assertEquals(NUM_SPECIAL, specialCount);
    }

    @Test
    void specialCellsShouldNotBeInReservedPositions() {
        board.getAllCells().stream()
                .filter(x -> x.getType() == CellType.SPECIAL)
                .map(Cell::getPosition)
                .forEach(pos -> {
                    Assertions.assertNotEquals(1, pos);
                    Assertions.assertNotEquals(32, pos);
                    Assertions.assertNotEquals(63, pos);
                });
    }

    @Test
    void getCellShouldReturnCellAtCorrectPosition() {
        for (int i = 1; i <= 63; i++) {
            Assertions.assertEquals(i, board.getCell(i).getPosition(),
                    "Cell" + i + "have wrong position number");
        }
    }

    @Test
    void differentSeedsShouldProduceDifferentBoards() {
        final GameConfig cfg1 = new GameConfig(63, 10, 42L);
        final GameConfig cfg2 = new GameConfig(63, 10, 84L);
        final BoardImpl board1 = new BoardImpl(cfg1);
        final BoardImpl board2 = new BoardImpl(cfg2);

        final List<Integer> specials1 = board1.getAllCells().stream()
                .filter(c -> c.getType() == CellType.SPECIAL)
                .map(Cell::getPosition)
                .toList();
        final List<Integer> specials2 = board2.getAllCells().stream()
                .filter(c -> c.getType() == CellType.SPECIAL)
                .map(Cell::getPosition)
                .toList();

        Assertions.assertNotEquals(specials1, specials2);
    }
}
