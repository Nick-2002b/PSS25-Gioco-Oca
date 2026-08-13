package it.unibo.giocooca.controller;

public interface MenuController {
    
    void start();
    /**
     * Avvia una nuova partita
     */
    void onStartNewGame();
    
    /**
     * Uscita dalla partita
     */
    void onQuit();

}
