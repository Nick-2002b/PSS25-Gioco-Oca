package it.unibo.giocooca.controller;

public interface SettingsController {
    double getMusicVolume();

    double getSfxVolume();

    int getNumSpecialCells();

    void onMusicVolumeChanger(double volume);

    void onSfxVolumeChanged(double volume);

    void onNumSpecialCellsChanger(int num);

    void onSave();

    void onBack();

    void show();
}
