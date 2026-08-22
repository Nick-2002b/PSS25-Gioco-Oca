package it.unibo.giocooca.model;

/**
 * Contratto che rappresenta una pedina del gioco.
 */
public interface Piece {
    /**
     * Restituisce il nome della pedina.
     *
     * @return il nome della pedina
     */
    String getName();

    /**
     * Restituisce il colore della pedina.
     *
     * @return il colore della pedina
     */
    String getColor();
}
