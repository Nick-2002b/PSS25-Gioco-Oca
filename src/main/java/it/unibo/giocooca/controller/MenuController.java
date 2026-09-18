package it.unibo.giocooca.controller;

/**
 * Contratto per il controller del menu principale.
 */
public interface MenuController {

    /**
     * Avvia e mostra il menu principale.
     */
    void start();

    /**
     * Avvia una nuova partita.
     */
    void onStartNewGame();

    /**
     * Apre la schermata delle impostazioni.
     */
    void onOpenSettings();

    /**
     * Apre la schermata delle regole.
     */
    void onShowRules();

    /**
     * Uscita dalla partita.
     */
    void onQuit();

}
