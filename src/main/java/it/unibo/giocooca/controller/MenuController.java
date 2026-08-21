package it.unibo.giocooca.controller;

public interface MenuController {
    
    void start();
    /**
     * Avvia una nuova partita
     */
    void onStartNewGame();

    void onOpenSettings();
    
    /**
     * Uscita dalla partita
     */
    void onQuit();

}
