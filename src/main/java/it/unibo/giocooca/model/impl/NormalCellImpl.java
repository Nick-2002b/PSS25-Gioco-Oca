package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Player;

/**
 * Casella normale: non applica alcun effetto al giocatore che ci atterra.
 */
public final class NormalCellImpl implements Cell {
    private final int position;

    /**
     * Crea una casella normale.
     *
     * @param position la posizione della casella sul tabellone
     */
    public NormalCellImpl(final int position) {
        this.position = position;
    }

    @Override
    public CellType getType() {
        return CellType.NORMAL;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void applyEffect(final Player player) {
        // None effect
    }
}
