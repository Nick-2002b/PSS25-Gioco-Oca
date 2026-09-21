package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.GameConfig;

import java.util.List;
import java.util.Random;

/**
 * Implementazione del tabellone di gioco.
 * Genera le caselle in base alla configurazione fornita da GameConfig.
 */
public final class BoardImpl implements Board {
    private final List<Cell> cells;

    /**
     * Costruisce il tabellone secondo la configurazione data.
     *
     * @param config la configurazione (size, numSpecialCells, seed)
     */
    public BoardImpl(final GameConfig config) {
        final Random random = new Random(config.seed());
        final int size = config.size();

        final Cell[] board = new Cell[size + 1];
        board[0] = new StartCellImpl();
        for (int i = 1; i <= size; i++) {
            board[i] = new NormalCellImpl(i); // 1-based index
        }

        board[GameConfig.PRISON_POSITION] = new PrisonCellImpl(GameConfig.PRISON_POSITION);

        final List<Integer> specialPositions = config.strategy()
                .getSpecialCellPositions(
                        size,
                        config.numSpecialCells(),
                        GameConfig.PRISON_POSITION,
                        random);

        for (final int pos : specialPositions) {
            final int offset = generateOffset(random);
            board[pos] = new SpecialCellImpl(pos, offset, size);
        }

        this.cells = List.of(board);
    }

    /**
     * Genera un offset casuale in [-8,-1] o [+1,+8].
     *
     * @param random il generatore di numeri casuali da usare
     * @return un offset casuale, positivo o negativo
     */
    private static int generateOffset(final Random random) {
        final int offset = random.nextInt(8) + 1;
        return random.nextBoolean() ? offset : -offset;
    }

    @Override
    public int getSize() {
        return this.cells.size();
    }

    /**
     * Restituisce la casella alla posizione indicata.
     *
     * @param position posizione 0-based (0 = start, da 1 a 63 le caselle di gioco)
     * @return la casella corrispondente
     * @throws IllegalArgumentException se la posizione è fuori range
     */
    @Override
    public Cell getCell(final int position) {
        if (position < 0 || position >= this.cells.size()) {
            throw new IllegalArgumentException("Position out of bounds:" + position);
        }
        return this.cells.get(position);
    }

    @Override
    public List<Cell> getAllCells() {
        return this.cells;
    }
}
