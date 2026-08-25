package it.unibo.giocooca.controller;

/**
 * Contratto per il controller di una partita in corso.
 */
public interface MatchController {

    /**
     * Inizializza e mostra la schermata di inizio partita.
     */
    void startMatch();
    /**
     * Lancia il dado, muove il giocatore, gestisce il caso di cella speciale, controlla se ha vinto, turno successivo.
     */
    void rollDice();
    /**
     * Torna al menù principale.
     */

    void quitMatch();

}
