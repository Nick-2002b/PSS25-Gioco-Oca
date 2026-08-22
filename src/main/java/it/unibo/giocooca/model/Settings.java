package it.unibo.giocooca.model;

import java.util.Set;

public final class Settings {
    private static final int MAX_SPECIAL_CELLS = 20;
    private static final double DEFAULT_MUSIC_VOLUME = 0.5;
    private static final double DEFAULT_SFX_VOLUME = 0.7;
    private static final int DEFAULT_SPECIAL_CELLS = 7;

    private double musicVolume;
    private double sfxVolume;
    private int numSpecialCells;

    public Settings() {
        this.musicVolume = DEFAULT_MUSIC_VOLUME;
        this.sfxVolume = DEFAULT_SFX_VOLUME;
        this.numSpecialCells = DEFAULT_SPECIAL_CELLS;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(final double musicVolume) {
        this.musicVolume = Math.clamp(musicVolume, 0.0, 1.0);
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(final double sfxVolume) {
        this.sfxVolume = Math.clamp(sfxVolume, 0.0, 1.0);
    }

    public int getNumSpecialCells() {
        return numSpecialCells;
    }

    public void setNumSpecialCells(final int numSpecialCells) {
        if (numSpecialCells < 1 || numSpecialCells > MAX_SPECIAL_CELLS) {
            throw new IllegalArgumentException(
                    "The special cells must be within the range of 1–20."
            );
        }
        this.numSpecialCells = numSpecialCells;
    }
}
