package it.unibo.giocooca.model;

/**
 * Impostazioni utente del gioco (audio e regole di partita).
 */
public final class Settings {
    private static final int MAX_SPECIAL_CELLS = 20;
    private static final double DEFAULT_MUSIC_VOLUME = 0.5;
    private static final double DEFAULT_SFX_VOLUME = 0.7;
    private static final int DEFAULT_SPECIAL_CELLS = 7;
    private static final int DEFAULT_NUM_DICE = 1;
    private static final int MIN_DICE = 1;
    private static final int MAX_DICE = 2;

    private boolean fixedPlacement;
    private double musicVolume;
    private double sfxVolume;
    private int numSpecialCells;
    private int numDice;

    /**
     * Crea le impostazioni con i valori di default.
     */
    public Settings() {
        this.musicVolume = DEFAULT_MUSIC_VOLUME;
        this.sfxVolume = DEFAULT_SFX_VOLUME;
        this.numSpecialCells = DEFAULT_SPECIAL_CELLS;
        this.numDice = DEFAULT_NUM_DICE;
    }

    public boolean isFixedPlacement() {
        return fixedPlacement;
    }

    public void setFixedPlacement(final boolean fixedPlacement) {
        this.fixedPlacement = fixedPlacement;
    }

    /**
     * Restituisce il numero di dadi
     *
     * @return il numero di dadi, tra 1 e 2
     */
    public int getNumDice() {
        return numDice;
    }

    /**
     * Imposta il numero di dadi.
     *
     * @param numDice il nuovo numero di dadi
     */
    public void setNumDice(final int numDice) {
        if (numDice < MIN_DICE || numDice > MAX_DICE) {
            throw new IllegalArgumentException(
                    "The number of dice must be between " + MIN_DICE + " and " + MAX_DICE + "."
            );
        }
        this.numDice = numDice;
    }

    /**
     * Restituisce il volume della musica.
     *
     * @return il volume della musica, tra 0.0 e 1.0
     */
    public double getMusicVolume() {
        return musicVolume;
    }

    /**
     * Imposta il volume della musica.
     *
     * @param musicVolume il nuovo volume della musica
     */
    public void setMusicVolume(final double musicVolume) {
        this.musicVolume = Math.clamp(musicVolume, 0.0, 1.0);
    }

    /**
     * Restituisce il numero di caselle speciali
     *
     * @return numero massimo di caselle speciali
     */
    public int getMaxSpecialCells() {
        return MAX_SPECIAL_CELLS;
    }

    /**
     * Restituisce il volume degli effetti sonori.
     *
     * @return il volume degli effetti sonori, tra 0.0 e 1.0
     */
    public double getSfxVolume() {
        return sfxVolume;
    }

    /**
     * Imposta il volume degli effetti sonori.
     *
     * @param sfxVolume il nuovo volume degli effetti sonori
     */
    public void setSfxVolume(final double sfxVolume) {
        this.sfxVolume = Math.clamp(sfxVolume, 0.0, 1.0);
    }

    /**
     * Restituisce il numero di caselle speciali configurate.
     *
     * @return il numero di caselle speciali
     */
    public int getNumSpecialCells() {
        return numSpecialCells;
    }

    /**
     * Imposta il numero di caselle speciali.
     *
     * @param numSpecialCells il nuovo numero di caselle speciali, tra 1 e 20
     */
    public void setNumSpecialCells(final int numSpecialCells) {
        if (numSpecialCells < 1 || numSpecialCells > MAX_SPECIAL_CELLS) {
            throw new IllegalArgumentException(
                    "The special cells must be within the range of 1-20."
            );
        }
        this.numSpecialCells = numSpecialCells;
    }
}
