package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Settings;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class SettingsManager {

    private static final String SETTINGS_DIR = System.getProperty("user.home") + File.separator + ".giocooca";
    private static final String SETTINGS_FILE = SETTINGS_DIR + File.separator + "settings.properties";

    private static final String KEY_MUSIC = "audio.musicVolume";
    private static final String KEY_SFX = "audio.sfxVolume";
    private static final String KEY_SPECIAL = "game.numSpecialCells";

    public Settings load() {
        final Settings settings = new Settings();
        final File file = new File(SETTINGS_FILE);

        if (!file.exists()) {
            return settings;
        }

        final Properties props = new Properties();
        try (InputStream in = new FileInputStream(file)) {
            props.load(in);
            settings.setMusicVolume( Double.parseDouble(props.getProperty(KEY_MUSIC, "0.5")));
            settings.setSfxVolume( Double.parseDouble(props.getProperty(KEY_SFX, "0.7")));
            settings.setNumSpecialCells((int) Double.parseDouble(props.getProperty(KEY_SPECIAL, "7")));
        } catch (IOException | NumberFormatException e) {
            // use default settings if the file is corrupted
        }

        return settings;
    }

    public void save(Settings settings) {
        final File dir = new File(SETTINGS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final Properties props = new Properties();
        props.setProperty(KEY_MUSIC, String.valueOf(settings.getMusicVolume()));
        props.setProperty(KEY_SFX, String.valueOf(settings.getSfxVolume()));
        props.setProperty(KEY_SPECIAL, String.valueOf(settings.getNumSpecialCells()));

        try (OutputStream out = new FileOutputStream(SETTINGS_FILE)) {
            props.store(out, "Goose Game - User settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
