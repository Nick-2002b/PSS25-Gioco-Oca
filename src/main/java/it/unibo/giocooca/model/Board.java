package it.unibo.giocooca.model;

import java.util.List;

/**
 * Contratto che rappresenta il tabellone di gioco
 */
public interface Board {
    int getSize();

    Cell getCell(int position);

    List<Cell> getAllCells();
}
