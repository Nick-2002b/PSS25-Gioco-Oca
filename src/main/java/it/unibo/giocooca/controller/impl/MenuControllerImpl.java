package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.audio.SoundEffect;
import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.MenuView;
import it.unibo.giocooca.view.impl.MenuViewImpl;
import javafx.application.Platform;

/**
 * Implementazione del controller del menu principale.
 */
public final class MenuControllerImpl implements MenuController {
    private final SceneManager sceneManager;
    private final MenuView view;

    /**
     * Crea il controller del menu principale.
     *
     * @param sceneManager il navigator dell'applicazione, usato per cambiare schermata
     */
    public MenuControllerImpl(final SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.view = new MenuViewImpl(sceneManager, this);
    }

    @Override
    public void start() {
        if (!SoundManager.getInstance().isMusicPlaying()) {
            SoundManager.getInstance().playMusic(SoundEffect.BACKGROUND_MUSIC);
        }
        this.view.show();
    }

    @Override
    public void onStartNewGame() {
        sceneManager.showSetup();
    }

    @Override
    public void onOpenSettings() {
        sceneManager.showSettings();
    }

    @Override
    public void onShowRules() {
        sceneManager.showRules();
    }

    @Override
    public void onQuit() {
        Platform.exit();
    }

}

