package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.controller.SettingsController;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.SettingsManager;
import it.unibo.giocooca.view.impl.SettingsViewImpl;
import javafx.stage.Stage;

public class SettingsControllerImpl implements SettingsController {

    private final Stage stage;
    private final Settings settings;
    private final SettingsManager settingsManager;
    private final MenuController menuController;

    public SettingsControllerImpl(
            Stage stage,
            Settings settings,
            MenuController menuController) {
        this.stage = stage;
        this.menuController = menuController;
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
    public void onMusicVolumeChanger(double volume) {
        settings.setMusicVolume(volume);
        SoundManager.getInstance().setMusicVolume(volume);
    }

    @Override
    public void onSfxVolumeChanged(double volume) {
        settings.setSfxVolume(volume);
        SoundManager.getInstance().setSfxVolume(volume);
    }

    @Override
    public void onNumSpecialCellsChanger(int num) {
        settings.setNumSpecialCells(num);
    }

    @Override
    public void onSave() {
        settingsManager.save(settings);
        menuController.start();
    }

    @Override
    public void onBack() {
        menuController.start();
    }

    @Override
    public void show() {
        new SettingsViewImpl(stage, this).show();
    }
}
