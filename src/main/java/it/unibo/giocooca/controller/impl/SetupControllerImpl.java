package it.unibo.giocooca.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unibo.giocooca.controller.SetupController;
import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Dice;
import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.impl.BoardImpl;
import it.unibo.giocooca.model.impl.DiceImpl;
import it.unibo.giocooca.model.impl.MatchImpl;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.SetupView;
import it.unibo.giocooca.view.impl.SetupViewImpl;

/**
 * Implementazione del controller della schermata di setup della partita.
 */
public final class SetupControllerImpl implements SetupController {
    private static final Logger LOGGER = Logger.getLogger(SetupControllerImpl.class.getName());
    private static final int DEFAULT_SPECIAL_CELLS = 6;

    private final SceneManager sceneManager;
    private final SetupView setupView;

    /**
     * Crea il controller di setup della partita.
     *
     * @param sceneManager il navigator dell'applicazione, usato per tornare al menu
     */
    public SetupControllerImpl(final SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.setupView = new SetupViewImpl(sceneManager, this);
    }

    @Override
    public void start() {
        this.setupView.show();
    }

    @Override
    public void onStartGame(final List<Player> players) {
        LOGGER.log(Level.INFO, "La partita e'' iniziata con n. {0} giocatori", players.size());
        final GameConfig config = GameConfig.defaultConfig(DEFAULT_SPECIAL_CELLS);
        final Board board = new BoardImpl(config);
        final Dice dice = new DiceImpl();
        final Match match = new MatchImpl(players, board, dice);
        this.sceneManager.showMatch(match);
    }

    @Override
    public void onBackToMenu() {
        sceneManager.showMenu();
    }
}
