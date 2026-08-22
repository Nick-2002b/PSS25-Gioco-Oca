package it.unibo.giocooca.controller;

import it.unibo.giocooca.model.Player;

import java.util.List;

/**
 * Contratto per il controller della schermata di setup della partita.
 */
public interface SetupController {

    /**
     * Avvia e mostra la schermata di setup.
     */
    void start();

    /**
     * Avvia la partita con i giocatori indicati.
     *
     * @param players i giocatori che parteciperanno alla partita
     */
    void onStartGame(List<Player> players);

    /**
     * Torna al menu principale.
     */
    void onBackToMenu();

}
