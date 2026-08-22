package it.unibo.giocooca.model;

/**
 * Contratto che rappresenta il Giocatore.
 */
public interface Player {
    /**
     * Restituisce la posizione attuale del giocatore.
     *
     * @return la posizione del giocatore
     */
    int getPosition();

    /**
     * Imposta la posizione del giocatore.
     *
     * @param position la nuova posizione del giocatore
     */
    void setPosition(int position);

    /**
     * Indica se il giocatore è in prigione.
     *
     * @return true se il giocatore è in prigione
     */
    boolean isInPrison();

    /**
     * Imposta lo stato di prigionia del giocatore.
     *
     * @param imprisoned per imprigionare il giocatore
     */
    void setInPrison(boolean imprisoned);

    /**
     * Restituisce il nickname del giocatore.
     *
     * @return il nickname del giocatore
     */
    String getNickName();

    /**
     * Restituisce la pedina del giocatore.
     *
     * @return la pedina del giocatore
     */
    Piece getPiece();

}
