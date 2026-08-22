package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.controller.SettingsController;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.SettingsManager;
import javafx.stage.Stage;

public class SettingsControllerImpl implements SettingsController {

    private final Stage stage;
    private final Settings settings;
    private final SettingsManager settingsManager;
    private MenuController menuController;

    public SettingsControllerImpl(
            Stage stage,
            MenuController menuController,
            Settings settings) {
        this.stage = stage;
        this.menuController = menuController;
        this.settings = settings;
        this.settingsManager = new SettingsManager();

    }
    @Override
    public double getMusicVolume() {
        return 0;
    }

    @Override
    public double getSfxVolume() {
        return 0;
    }

    @Override
    public int getNumSpecialCells() {
        return 0;
    }

    @Override
    public void onMusicVolumeChanger(double volume) {

    }

    @Override
    public void onSfxVolumeChanged(double volume) {

    }

    @Override
    public void onNumSpecialCellsChanger(int num) {

    }

    @Override
    public void onSave() {

    }

    @Override
    public void onBack() {

    }

    @Override
    public void show() {

    }
}
