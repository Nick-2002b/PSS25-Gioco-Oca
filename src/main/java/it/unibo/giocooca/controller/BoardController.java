package it.unibo.giocooca.controller;


import java.util.Map;

/**
 * Contratto del tabellone
 */
public interface BoardController {

    /**
     * Dimensione del tabellone (63).
     */
    int getBoardSize();

    /**
     * Tipo della casella alla posizione data.
     * Restituisce "NORMAL", "SPECIAL" o "PRISON".
     *
     * @param position posizione 1-based
     * @return il tipo come stringa
     */
    String getCellType(int position);

    /**
     * Posizioni attuali dei giocatori.
     *
     * @return mappa colore → posizione
     */
    Map<String, Integer> getPlayerPositions();

}
