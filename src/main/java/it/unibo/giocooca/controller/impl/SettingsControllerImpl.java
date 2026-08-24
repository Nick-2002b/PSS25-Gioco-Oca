package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.SettingsController;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.SettingsManager;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.impl.SettingsViewImpl;

/**
 * Implementazione del controller della schermata delle impostazioni.
 */
public final class SettingsControllerImpl implements SettingsController {

    private final SceneManager sceneManager;
    private final Settings settings;
    private final SettingsManager settingsManager;

    /**
     * Crea il controller delle impostazioni.
     *
     * @param sceneManager il navigator dell'applicazione, usato per tornare al menu
     * @param settings     le impostazioni correnti da modificare
     */
    public SettingsControllerImpl(final SceneManager sceneManager, final Settings settings) {
        this.sceneManager = sceneManager;
        this.settings = settings;
        this.settingsManager = new SettingsManager();
    }

    @Override
    public double getMusicVolume() {
        return settings.getMusicVolume();
    }

    @Override
    public double getSfxVolume() {
        return settings.getSfxVolume();
    }

    @Override
    public int getNumSpecialCells() {
        return settings.getNumSpecialCells();
    }

    @Override
    public void onMusicVolumeChanger(final double volume) {
        settings.setMusicVolume(volume);
        SoundManager.getInstance().setMusicVolume(volume);
    }

    @Override
    public void onSfxVolumeChanged(final double volume) {
        settings.setSfxVolume(volume);
        SoundManager.getInstance().setSfxVolume(volume);
    }

    @Override
    public void onNumSpecialCellsChanger(final int num) {
        settings.setNumSpecialCells(num);
    }

    @Override
    public void onSave() {
        settingsManager.save(settings);
        sceneManager.showMenu();
    }

    @Override
    public void onBack() {
        sceneManager.showMenu();
    }

    @Override
    public void show() {
        new SettingsViewImpl(sceneManager, this).show();
    }
}
