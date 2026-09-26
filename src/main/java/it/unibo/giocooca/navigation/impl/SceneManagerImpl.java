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
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Region;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Implementazione del Navigator Controller.
 *
 */
public final class SceneManagerImpl implements SceneManager {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 800;
    private static final int WINDOW_MARGIN_WIDTH = 20;
    private static final int WINDOW_MARGIN_HEIGHT = 40;
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
            region.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
            region.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
            region.setMaxSize(SCENE_WIDTH, SCENE_HEIGHT);
        }
        stage.setTitle(title);
        stage.getIcons().add(logo);

        final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        final double maxAvailableWidth = screenBounds.getWidth() - WINDOW_MARGIN_WIDTH;
        final double maxAvailableHeight = screenBounds.getHeight() - WINDOW_MARGIN_HEIGHT;

        final double scaleX = maxAvailableWidth / SCENE_WIDTH;
        final double scaleY = maxAvailableHeight / SCENE_HEIGHT;
        final double scale = Math.min(1.0, Math.min(scaleX, scaleY));

        if (scale < 1.0) {
            root.getTransforms().add(new Scale(scale, scale, 0, 0));
        }
        final Parent finalRoot = scale < 1.0 ? new Group(root) : root;

        if (stage.getScene() == null) {
            stage.setScene(new Scene(finalRoot, SCENE_WIDTH * scale, SCENE_HEIGHT * scale));
            stage.setResizable(false);
        } else {
            stage.getScene().setRoot(finalRoot);
        }
        stage.show();
    }
}
