package it.unibo.giocooca.model;

import java.util.List;

/**
 * Contratto che rappresenta il tabellone di gioco.
 */
public interface Board {
    /**
     * Restituisce il numero totale di caselle del tabellone.
     *
     * @return la dimensione del tabellone
     */
    int getSize();

    /**
     * Restituisce la casella alla posizione indicata.
     *
     * @param position la posizione della casella richiesta
     * @return la casella corrispondente
     */
    Cell getCell(int position);

    /**
     * Restituisce tutte le caselle del tabellone.
     *
     * @return la lista di tutte le caselle
     */
    List<Cell> getAllCells();
}
