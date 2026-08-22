package it.unibo.giocooca.model;

/**
 * Contratto che rappresenta le celle
 */
public interface Cell {
    CellType getType();

    int getPosition();

    void applyEffect(Player player);
}
