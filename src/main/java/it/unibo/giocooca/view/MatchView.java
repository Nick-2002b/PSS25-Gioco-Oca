package it.unibo.giocooca.view;

/**
 * Contratto per la vista di una partita in corso.
 */
public interface MatchView {
    /**
     * Mostra un messaggio generico all'utente.
     *
     * @param message il messaggio da mostrare
     */
    void showMessage(String message);

    /**
     * Mostra il risultato del lancio del dado.
     *
     * @param result il valore ottenuto dal lancio
     */
    void showDiceResult(int result);

    /**
     * Mostra il vincitore della partita.
     *
     * @param winner il nickname del vincitore
     */
    void showWinner(String winner);

    /**
     * Mostra il giocatore di turno.
     *
     * @param player il nickname del giocatore di turno
     */
    void showCurrentTurn(String player);
}
