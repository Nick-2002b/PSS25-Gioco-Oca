package it.unibo.giocooca.model;

/**
 * Contratto che rappresenta le celle.
 */
public interface Cell {
    /**
     * Restituisce il tipo della casella.
     *
     * @return il tipo della casella
     */
    CellType getType();

    /**
     * Restituisce la posizione della casella sul tabellone.
     *
     * @return la posizione della casella
     */
    int getPosition();

    /**
     * Applica l'effetto della casella al giocatore che ci atterra.
     *
     * @param player il giocatore su cui applicare l'effetto
     */
    void applyEffect(Player player);
}
