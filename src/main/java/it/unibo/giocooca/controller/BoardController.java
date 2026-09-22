package it.unibo.giocooca.controller;

import it.unibo.giocooca.model.CellType;

import java.util.Map;

/**
 * Contratto del tabellone.
 */
public interface BoardController {

    /**
     * Dimensione del tabellone (63).
     *
     * @return la dimensione del tabellone
     */
    int getBoardSize();

    /**
     * Tipo della casella alla posizione data.
     * Restituisce "NORMAL", "SPECIAL" o "PRISON".
     *
     * @param position posizione 1-based
     * @return la tipologia di cella
     */
    CellType getCellType(int position);

    /**
     * Offset (bonus/malus) della casella alla posizione data.
     * Restituisce 0 per caselle che non ne prevedono uno.
     *
     * @param position posizione 1-based
     * @return l'offset della casella
     */
    int getCellOffset(int position);

    /**
     * Posizioni attuali dei giocatori.
     *
     * @return mappa colore → posizione
     */
    Map<String, Integer> getPlayerPositions();

}
