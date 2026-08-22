package it.unibo.giocooca.model;

/**
 * Configurazione immutabile per la generazione del tabellone.
 *
 * @param size            numero totale di caselle
 * @param numSpecialCells quante caselle speciali generare
 * @param seed            seed per la generazione casuale (riproducibilita)
 */
public record GameConfig(int size, int numSpecialCells, long seed) {
    public static final int PRISON_POSITION = 32;
    private static final int DEFAULT_BOARD_SIZE = 63;

    public GameConfig {
        if (size <= 0) {
            throw new IllegalArgumentException("The board size must be greater than 0.");
        }
    }


    public static GameConfig defaultConfig(final int numSpecialCells) {
        return new GameConfig(DEFAULT_BOARD_SIZE, numSpecialCells, System.currentTimeMillis());
    }
}
