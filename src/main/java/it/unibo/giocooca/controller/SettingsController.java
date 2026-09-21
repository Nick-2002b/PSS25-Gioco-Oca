package it.unibo.giocooca.controller;

/**
 * Contratto per il controller della schermata delle impostazioni.
 */
public interface SettingsController {

    /**
     * Indica se il posizionamento delle pedine è fisso.
     *
     * @return true se il posizionamento è fisso, false se è casuale
     */
    boolean isFixedPlacement();

    /**
     * Notifica un cambiamento della modalità di posizionamento.
     *
     * @param fixed true se il posizionamento è fisso, false se è casuale
     */
    void onPlacementChanged(boolean fixed);

    /**
     * Restituisce il numero di dadi da utilizzare.
     *
     * @return numero di dadi
     */
    int getNumDice();

    /**
     * Restituisce il massimo di caselle speciali
     * da poter piazzare sul percorso.
     *
     * @return massimo di caselle speciali
     */
    int getMaxSpecialCells();

    /**
     * Restituisce il volume della musica.
     *
     * @return il volume della musica
     */
    double getMusicVolume();

    /**
     * Restituisce il volume degli effetti sonori.
     *
     * @return il volume degli effetti sonori
     */
    double getSfxVolume();

    /**
     * Restituisce il numero di caselle speciali configurate.
     *
     * @return il numero di caselle speciali
     */
    int getNumSpecialCells();

    /**
     * Notifica un cambiamento del volume della musica.
     *
     * @param volume il nuovo volume della musica
     */
    void onMusicVolumeChanger(double volume);

    /**
     * Notifica un cambiamento del numero di dadi da utilizzare.
     *
     * @param num numero di dadi
     */
    void onNumDiceChanged(int num);

    /**
     * Notifica un cambiamento del volume degli effetti sonori.
     *
     * @param volume il nuovo volume degli effetti sonori
     */
    void onSfxVolumeChanged(double volume);

    /**
     * Notifica un cambiamento del numero di caselle speciali.
     *
     * @param num il nuovo numero di caselle speciali
     */
    void onNumSpecialCellsChanger(int num);

    /**
     * Salva le impostazioni correnti.
     */
    void onSave();

    /**
     * Torna alla schermata precedente senza salvare.
     */
    void onBack();

    /**
     * Mostra la schermata delle impostazioni.
     */
    void show();
}
