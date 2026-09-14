package it.unibo.giocooca.view;

import javafx.scene.layout.Pane;

import java.util.Map;

import java.util.List;

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
     * Aggiorna la posizione visiva delle pedine dei giocatori.
     *
     * @param playerPositions mappa colore pedina -> posizione sul tabellone
     * @param movingPieceColor colore della pedina da animare
     * @param intermediatePosition lista delle posizioni intermedie da attraversare
     * @param onIntermediateReached callback quando la pedina raggiunge la posizione intermedia
     * @param onAnimationFinished callback quando la pedina raggiunge la posizione finale
     */
    void updatePlayerPositions(
            Map<String, Integer> playerPositions,
            String movingPieceColor,
            List<Integer> intermediatePosition,
            Runnable onIntermediateReached,
            Runnable onAnimationFinished);
}
