package it.unibo.giocooca.audio;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestisce la riproduzione della musica e degli effetti sonori del gioco.
 */
public final class SoundManager {
    private static final Logger LOGGER = Logger.getLogger(SoundManager.class.getName());
    private static final SoundManager INSTANCE = new SoundManager();

    private MediaPlayer musicPlayer;
    private double musicVolume;
    private double sfxVolume;

    private SoundManager() {
    }

    /**
     * Restituisce l'istanza singleton del gestore audio.
     *
     * @return l'istanza singleton di {@code SoundManager}
     * */
    public static SoundManager getInstance() {
        return INSTANCE;
    }

    /**
     * Avvia la riproduzione in loop di un brano musicale, fermando l'eventuale musica in corso.
     *
     * @param resourcePath l'effetto sonoro (brano) da riprodurre
     */
    public void playMusic(final SoundEffect resourcePath) {
        stopMusic();
        try {
            final var url = getClass().getResource(resourcePath.getResourcePath());
            if (url == null) {
                return;
            }
            final MediaPlayer player = new MediaPlayer(new Media(url.toExternalForm()));
            player.setVolume(musicVolume);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
            this.musicPlayer = player;
        } catch (final MediaException e) {
            LOGGER.log(Level.WARNING, "Unable to play music: the game continues without audio.", e);
        }
    }

    /**
     * Ferma la riproduzione della musica corrente, se presente.
     */
    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.dispose();
            musicPlayer = null;
        }
    }

    /**
     * Riproduce un effetto sonoro una sola volta.
     *
     * @param effect l'effetto sonoro da riprodurre
     */
    public void playSfx(final SoundEffect effect) {
        try {
            final var url = getClass().getResource(effect.getResourcePath());
            if (url == null) {
                return;
            }
            final AudioClip sfx = new AudioClip(url.toExternalForm());
            sfx.setVolume(sfxVolume);
            sfx.play();
        } catch (final MediaException e) {
            LOGGER.log(Level.WARNING, "Unable to play the sound effect: the game continues without audio.", e);
        }
    }

    /**
     * Imposta il volume della musica.
     *
     * @param volume il nuovo volume della musica, tra 0.0 e 1.0
     */
    public void setMusicVolume(final double volume) {
        this.musicVolume = Math.clamp(volume, 0.0, 1.0);
        if (musicPlayer != null) {
            musicPlayer.setVolume(this.musicVolume);
        }
    }

    /**
     * Imposta il volume degli effetti sonori.
     *
     * @param volume il nuovo volume degli effetti sonori, tra 0.0 e 1.0
     */
    public void setSfxVolume(final double volume) {
        this.sfxVolume = Math.clamp(volume, 0.0, 1.0);
    }

    /**
     * Indica se la musica è attualmente in riproduzione.
     *
     * @return true se la musica è in riproduzione
     */
    public boolean isMusicPlaying() {
        return musicPlayer != null
                && musicPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
