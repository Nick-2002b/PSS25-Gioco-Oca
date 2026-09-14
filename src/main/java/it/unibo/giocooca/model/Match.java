package it.unibo.giocooca.model;

import java.util.List;

/**
 * Contratto che rappresenta il match.
 */
public interface Match {
    /**
     * Lancia il dado per il giocatore corrente.
     *
     * @return il valore ottenuto dal lancio
     */
    int rollDice();

    /**
     * Sposta il giocatore corrente di un certo numero di passi.
     *
     * @param steps il numero di passi da compiere
     */
    void moveCurrentPlayer(int steps);

    /**
     * Applica l'effetto della casella su cui si trova il giocatore indicato.
     *
     * @param player il giocatore su cui applicare l'effetto
     */
    void applyCurrentCellEffect(Player player);

    /**
     * Passa il turno al giocatore successivo.
     */
    void nextTurn();

    /**
     * Indica se la partita è terminata.
     *
     * @return se la partita è terminata
     */
    boolean isGameOver();

    /**
     * Restituisce il giocatore di turno.
     *
     * @return il giocatore corrente
     */
    Player getCurrentPlayer();

    /**
     * Restituisce il vincitore della partita, se presente.
     *
     * @return il vincitore, oppure null se la partita non è terminata
     */
    Player getWinner();

    /**
     * Restituisce l'elenco dei giocatori della partita.
     *
     * @return la lista dei giocatori
     */
    List<Player> getPlayers();

    /**
     * Restituisce il tabellone della partita.
     *
     * @return il tabellone
     */
    Board getBoard();
    /**
     * Restituisce la lista delle posizioni dei salti del giocatore quando incontra le caselle speciali
     * @return lista delle posizioni dei salti
     */
    List<Integer> getLastMovePositions();
}
