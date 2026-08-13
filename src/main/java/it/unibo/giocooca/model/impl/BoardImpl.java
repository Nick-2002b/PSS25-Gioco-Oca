package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.GameConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementazione del tabellone di gioco.
 * Genera le caselle in base alla configurazione fornita da GameConfig.
 */
public class BoardImpl implements Board {
    private final List<Cell> cells;

    /**
     * Costruisce il tabellone secondo la configurazione data.
     *
     * @param config la configurazione (size, numSpecialCells, seed)
     */
    public BoardImpl(GameConfig config) {
        final Random random = new Random(config.seed());
        final int size = config.size();

        final Cell[] board = new Cell[size];
        for (int i = 0; i < size; i++) {
            board[i] = new NormalCellImpl(i + 1); // 1-based index
        }

        board[GameConfig.PRISON_POSITION - 1] = new PrisonCellImpl(GameConfig.PRISON_POSITION);

        final List<Integer> freePos = new ArrayList<>();
        for (int pos = 2; pos < size; pos++) {
            if (pos != GameConfig.PRISON_POSITION) {
                freePos.add(pos);
            }
        }

        Collections.shuffle(freePos, random);

        for (int i = 0; i < config.numSpecialCells(); i++) {
            final int pos = freePos.get(i);
            final int offset = generateOffset(random);
            board[pos - 1] = new SpecialCellImpl(pos, offset, size);
        }

        this.cells = List.of(board);
    }

    /**
     * Genera un offset casuale in [-8,-1] o [+1,+8].
     */
    private static int generateOffset(Random random) {
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
     * @param position posizione 1-based (da 1 a 63)
     * @return la casella corrispondente
     * @throws IllegalArgumentException se la posizione è fuori range
     */
    @Override
    public Cell getCell(int position) {
        if (position < 1 || position > this.cells.size()) {
            throw new IllegalArgumentException( "Position out of bounds:" + position);
        }
        return this.cells.get(position - 1);
    }

    @Override
    public List<Cell> getAllCells() {
        return this.cells;
    }
}
