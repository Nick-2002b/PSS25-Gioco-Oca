package it.unibo.giocooca.view;

import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * Contratto per la vista del tabellone di gioco.
 */
public interface BoardView {

    /**
     * Restituisce il nodo grafico del tabellone da inserire nel layout.
     *
     * @return il GridPane che rappresenta il tabellone
     */
    Pane getBoard();

    /**
     * Aggiorna la posizione visiva delle pedine dei giocatori..
     *
     * @param playerPositions mappa colore pedina → posizione sul tabellone
     */
    void updatePlayerPositions(Map<String, Integer> playerPositions);
}
