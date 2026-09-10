package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Player;

/**
 * Casella di partenza (posizione 0): non applica alcun effetto,
 */
public final class StartCellImpl implements Cell {

    @Override
    public CellType getType() {
        return CellType.START;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public void applyEffect(final Player player) {
        // None effect
    }
}
