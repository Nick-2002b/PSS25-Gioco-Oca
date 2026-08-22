package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Carica e salva le impostazioni utente su file.
 */
public final class SettingsManager {

    private static final Logger LOGGER = Logger.getLogger(SettingsManager.class.getName());

    private static final String SETTINGS_DIR = System.getProperty("user.home") + File.separator + ".giocooca";
    private static final String SETTINGS_FILE = SETTINGS_DIR + File.separator + "settings.properties";

    private static final String KEY_MUSIC = "audio.musicVolume";
    private static final String KEY_SFX = "audio.sfxVolume";
    private static final String KEY_SPECIAL = "game.numSpecialCells";

    /**
     * Carica le impostazioni salvate su file, oppure restituisce quelle di default se il file non esiste
     * o è corrotto.
     *
     * @return le impostazioni caricate
     */
    public Settings load() {
        final Settings settings = new Settings();
        final File file = new File(SETTINGS_FILE);

        if (!file.exists()) {
            return settings;
        }

        final Properties props = new Properties();
        try (InputStream in = new FileInputStream(file)) {
            props.load(in);
            settings.setMusicVolume(Double.parseDouble(props.getProperty(KEY_MUSIC, "0.5")));
            settings.setSfxVolume(Double.parseDouble(props.getProperty(KEY_SFX, "0.7")));
            settings.setNumSpecialCells((int) Double.parseDouble(props.getProperty(KEY_SPECIAL, "7")));
        } catch (final IOException | NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Unable to load settings; default values will be used.", e);
        }

        return settings;
    }

    /**
     * Salva le impostazioni indicate su file.
     *
     * @param settings le impostazioni da salvare
     */
    public void save(final Settings settings) {
        final File dir = new File(SETTINGS_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            LOGGER.log(
                    Level.WARNING,
                    "Unable to create the settings folder:Impossibile creare la cartella delle impostazioni: {0}", SETTINGS_DIR);
        }
        final Properties props = new Properties();
        props.setProperty(KEY_MUSIC, String.valueOf(settings.getMusicVolume()));
        props.setProperty(KEY_SFX, String.valueOf(settings.getSfxVolume()));
        props.setProperty(KEY_SPECIAL, String.valueOf(settings.getNumSpecialCells()));

        try (OutputStream out = new FileOutputStream(SETTINGS_FILE)) {
            props.store(out, "Goose Game - User settings");
        } catch (final IOException e) {
            LOGGER.log(Level.WARNING, "Unable to save the settings: ", e);
        }
    }
}
