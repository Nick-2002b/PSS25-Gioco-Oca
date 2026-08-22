package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Player;

/**
 * Casella prigione: quando un giocatore ci atterra, viene messo in prigione.
 */
public final class PrisonCellImpl implements Cell {
    private final int position;

    /**
     * Crea una casella prigione.
     *
     * @param position la posizione della casella sul tabellone
     */
    public PrisonCellImpl(final int position) {
        this.position = position;
    }

    @Override
    public CellType getType() {
        return CellType.PRISON;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void applyEffect(final Player player) {
        player.setInPrison(true);
    }
}
