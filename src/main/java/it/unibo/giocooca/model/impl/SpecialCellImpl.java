package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Player;

/**
 * Casella speciale: applica un bonus (offset positivo) o malus (offset negativo)
 * al giocatore che ci atterra.
 */
public class SpecialCellImpl implements Cell {
    private final int position;
    private final int offset;
    private final int boardSize;

    /**
     * Crea una casella speciale.
     *
     * @param position  posizione della casella sul tabellone
     * @param offset    bonus/malus da applicare
     * @param boardSize dimensione totale del tabellone
     * @throws IllegalArgumentException se offset è 0 o fuori range [-8, +8]
     */
    public SpecialCellImpl(int position, int offset, int boardSize) {
        if (offset == 0 || offset < -8 || offset > 8) {
            throw new IllegalArgumentException(
                    "Offset must be between -8 and +8. Received: " + offset
            );
        }
        this.position = position;
        this.offset = offset;
        this.boardSize = boardSize;
    }

    @Override
    public CellType getType() {
        return CellType.SPECIAL;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void applyEffect(Player player) {
        final int newPos = this.position + this.offset;
        if(newPos >= 1 && newPos < this.boardSize) {
            player.setPosition(newPos);
        }
    }
}
