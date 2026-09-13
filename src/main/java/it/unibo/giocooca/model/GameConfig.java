package it.unibo.giocooca.model;

import it.unibo.giocooca.model.impl.RandomPlacementStrategy;

/**
 * Configurazione immutabile per la generazione del tabellone.
 *
 * @param size            numero totale di caselle
 * @param numSpecialCells quante caselle speciali generare
 * @param seed            seed per la generazione casuale (riproducibilita)
 * @param strategy        strategia di posizionamento delle caselle speciali
 */
public record GameConfig(int size, int numSpecialCells, long seed, PlacementStrategy strategy) {
    public static final int PRISON_POSITION = 32;
    private static final int DEFAULT_BOARD_SIZE = 63;

    /**
     * Valida i parametri della configurazione.
     */
    public GameConfig {
        if (size <= 0) {
            throw new IllegalArgumentException("The board size must be greater than 0.");
        }
    }

    /**
     * Crea una configurazione con la dimensione di default del tabellone e posizionamento casuale.
     *
     * @param numSpecialCells quante caselle speciali generare
     * @return una nuova configurazione con seed casuale
     */
    public static GameConfig defaultConfig(final int numSpecialCells) {
        return new GameConfig(DEFAULT_BOARD_SIZE, numSpecialCells, System.currentTimeMillis(), new RandomPlacementStrategy());
    }

    /**
     * Crea una configurazione con la dimensione di default del tabellone e strategia personalizzata.
     *
     * @param numSpecialCells quante caselle speciali generare
     * @return una nuova configurazione con seed casuale
     */
    public static GameConfig defaultConfig(final int numSpecialCells, final PlacementStrategy strategy) {
        return new GameConfig(DEFAULT_BOARD_SIZE, numSpecialCells, System.currentTimeMillis(), strategy);
    }
}
