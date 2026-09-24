package it.unibo.giocooca.audio;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestisce la riproduzione della musica e degli effetti sonori del gioco.
 */
public final class SoundManager {
    private static final Logger LOGGER = Logger.getLogger(SoundManager.class.getName());
    private static final SoundManager INSTANCE = new SoundManager();

    private Clip musicClip;
    private double musicVolume;
    private double sfxVolume;

    private SoundManager() {
    }

    /**
     * Restituisce l'istanza singleton del gestore audio.
     *
     * @return l'istanza singleton di {@code SoundManager}
     *
     */
    @SuppressFBWarnings(value = "MS_EXPOSE_REP",
            justification = "Singleton pattern: returning the unique instance is the intended behavior")
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
            final var is = getClass().getResourceAsStream(resourcePath.getResourcePath());
            if (is == null) {
                return;
            }
            final Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(is));
            applyVolume(clip, musicVolume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            this.musicClip = clip;
        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.log(Level.WARNING, "Unable to play music: the game continues without audio.", e);
        }
    }

    /**
     * Ferma la riproduzione della musica corrente, se presente.
     */
    public void stopMusic() {
        if (musicClip != null) {
            musicClip.close();
            musicClip = null;
        }
    }

    /**
     * Riproduce un effetto sonoro una sola volta.
     *
     * @param effect l'effetto sonoro da riprodurre
     */
    public void playSfx(final SoundEffect effect) {
        try {
            final var is = getClass().getResourceAsStream(effect.getResourcePath());
            if (is == null) {
                return;
            }
            final Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(is));
            applyVolume(clip, sfxVolume);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
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
        if (musicClip != null) {
            applyVolume(musicClip, this.musicVolume);
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
        return musicClip != null && musicClip.isRunning();
    }

    /**
     * Applica il volume (0.0–1.0) a un Clip.
     *
     * @param clip il clip a cui applicare il volume
     * @param volume il volume lineare tra 0.0 e 1.0
     */
    private static void applyVolume(final Clip clip, final double volume) {
        final FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        final float minGain = gain.getMinimum();
        final float maxGain = gain.getMaximum();
        final float dB = (float) (20.0 * Math.log10(Math.max(volume, 1e-4)));
        gain.setValue(Math.clamp(dB, minGain, maxGain));
    }
}
