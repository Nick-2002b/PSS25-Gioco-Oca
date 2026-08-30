package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Player;

/**
 * Casella speciale: applica un bonus (offset positivo) o malus (offset negativo)
 * al giocatore che ci atterra.
 */
public final class SpecialCellImpl implements Cell {
    private static final int MAX_OFFSET = 8;

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
  public SpecialCellImpl(final int position, final int offset, final int boardSize) {
        if (offset == 0 || offset < -MAX_OFFSET || offset > MAX_OFFSET) {
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
    public void applyEffect(final Player player) {
        final int newPos = player.getPosition() + this.offset;
        if (newPos <= 1) {
            player.setPosition(1);
            return;
        }
        if (newPos >= this.boardSize) {
            player.setPosition(this.boardSize);
            return;
        }
        player.setPosition(newPos);
    }

    @Override
    public int getOffset() {
        return this.offset;
    }
}
