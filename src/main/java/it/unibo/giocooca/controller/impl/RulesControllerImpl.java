package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.controller.RulesController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.RulesView;
import it.unibo.giocooca.view.impl.RulesViewImpl;

/**
 * Implementazione del controller della schermata delle regole.
 */
public final class RulesControllerImpl implements RulesController {
    private final SceneManager sceneManager;
    private final RulesView view;

    /**
     * Crea il controller delle regole.
     *
     * @param sceneManager manager delle scene
     */
    public RulesControllerImpl(final SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.view = new RulesViewImpl(sceneManager, this);
    }

    @Override
    public void show() {
        this.view.show();
    }

    @Override
    public void onBackToMenu() {
        this.sceneManager.showMenu();
    }
}
