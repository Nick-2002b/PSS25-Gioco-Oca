package it.unibo.giocooca.navigation.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.impl.MenuControllerImpl;
import it.unibo.giocooca.controller.impl.RulesControllerImpl;
import it.unibo.giocooca.controller.impl.SettingsControllerImpl;
import it.unibo.giocooca.controller.impl.SetupControllerImpl;
import it.unibo.giocooca.controller.impl.MatchControllerImpl;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.SettingsManager;
import it.unibo.giocooca.navigation.SceneManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Region;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

/**
 * Implementazione del Navigator Controller.
 *
 */
public final class SceneManagerImpl implements SceneManager {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 800;
    private static final Background APP_BACKGROUND = new Background(new BackgroundImage(
            new Image("/images/appBackground.png"),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
    ));
    private final Image logo = new Image("/images/ocaLogo.png");
    private final Stage stage;
    private final Settings settings;

    /**
     * Crea il navigator dell'applicazione.
     *
     * @param stage la finestra principale
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
            justification = "JavaFX Stage is a singleton window handle and cannot be defensively copied")
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
    public void showRules() {
        new RulesControllerImpl(this).show();
    }

    @Override
    public void showMatch(final Match match) {
        new MatchControllerImpl(this, match).startMatch();
    }

    @Override
    public void render(final Parent root, final String title) {
        if (root instanceof Region region) {
            region.setBackground(APP_BACKGROUND);
        }
        stage.setTitle(title);
        stage.getIcons().add(logo);
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            stage.setResizable(false);
        } else {
            stage.getScene().setRoot(root);
        }
        stage.show();
    }
}
