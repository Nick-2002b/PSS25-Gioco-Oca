package it.unibo.giocooca.audio;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    private static final SoundManager INSTANCE = new SoundManager();

    private MediaPlayer musicPlayer;
    private double musicVolume;
    private double sfxVolume;

    private SoundManager() { }

    public static SoundManager getInstance() {
        return INSTANCE;
    }

    public void playMusic(String resourcePath) {
        stopMusic();
        try {
            final var url = getClass().getResource(resourcePath);
            if (url == null) { return; }
            final MediaPlayer player = new MediaPlayer(new Media(url.toExternalForm()));
            player.setVolume(musicVolume);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
            this.musicPlayer = player;
        } catch (Exception e) {
            // The games continue without audio
        }
    }

    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.dispose();
            musicPlayer = null;
        }
    }

    public void playSfx(SoundEffect effect) {
        try {
            final var url = getClass().getResource(effect.getResourcePath());
            if (url == null) { return; }
            final AudioClip sfx = new AudioClip(url.toExternalForm());
            sfx.setVolume(sfxVolume);
            sfx.play();
        } catch (Exception e) {
            // The games continue without audio
        }
    }

    public void setMusicVolume(double volume) {
        this.musicVolume = Math.clamp(volume, 0.0, 1.0);
        if (musicPlayer != null) {
            musicPlayer.setVolume(this.musicVolume);
        }
    }

    public void setSfxVolume(double volume) {
        this.sfxVolume = Math.clamp(volume, 0.0, 1.0);
    }
}
