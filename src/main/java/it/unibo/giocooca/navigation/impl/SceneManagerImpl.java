package it.unibo.giocooca.navigation.impl;

import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.impl.MatchControllerImpl;
import it.unibo.giocooca.controller.impl.MenuControllerImpl;
import it.unibo.giocooca.controller.impl.SettingsControllerImpl;
import it.unibo.giocooca.controller.impl.SetupControllerImpl;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.SettingsManager;
import it.unibo.giocooca.navigation.SceneManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Implementazione del Navigator Controller.
 *
 */
public final class SceneManagerImpl implements SceneManager {
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    private final Stage stage;
    private final Settings settings;

    /**
     * Crea il navigator dell'applicazione.
     *
     * @param stage la finestra principale
     */
    public SceneManagerImpl(final Stage stage) {
        this.stage = stage;
        this.settings = new SettingsManager().load();
        SoundManager.getInstance().setMusicVolume(settings.getMusicVolume());
        SoundManager.getInstance().setSfxVolume(settings.getSfxVolume());
    }

    @Override
    public void showMenu() {
        new MenuControllerImpl(this).start();
    }

    @Override
    public void showSettings() {
        new SettingsControllerImpl(this, settings).show();
    }

    @Override
    public void showSetup() {
        new SetupControllerImpl(this, settings).start();
    }

    @Override
    public void showMatch(final Match match){
        new MatchControllerImpl(this, match).startMatch();
    }

    @Override
    public void render(final Parent root, final String title) {
        stage.setTitle(title);
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            stage.setMaximized(true);
        } else {
            stage.getScene().setRoot(root);
        }
        stage.show();
    }
}
