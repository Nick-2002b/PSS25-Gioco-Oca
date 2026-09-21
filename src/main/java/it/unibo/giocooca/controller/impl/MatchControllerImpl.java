package it.unibo.giocooca.controller.impl;

import java.util.List;

import it.unibo.giocooca.audio.SoundEffect;
import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.BoardController;
import it.unibo.giocooca.controller.MatchController;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.BoardView;
import it.unibo.giocooca.view.MatchView;
import it.unibo.giocooca.view.impl.BoardViewImpl;
import it.unibo.giocooca.view.impl.MatchViewImpl;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Implementazione del controller che gestisce il flusso di una partita,
 * coordinando vista, turno dei giocatori e aggiornamento del tabellone.
 */
public final class MatchControllerImpl implements MatchController {
    private final SceneManager sceneManager;
    private final Match match;
    private final MatchView view;
    private final BoardView boardView;
    private final BoardController boardController;
    private boolean isMatchActive = true;
    private PauseTransition currentPause;

    /**
     * Controller della partita
     *
     * @param sceneManager per gestire le schermate
     * @param match        la partita in corso
     */

    public MatchControllerImpl(final SceneManager sceneManager, final Match match) {
        if (sceneManager == null) {
            throw new IllegalArgumentException("SceneManager cannot be null");
        }
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        this.boardController = new BoardControllerImpl(match.getBoard(), match.getPlayers());
        this.boardView = new BoardViewImpl(boardController);

        this.sceneManager = sceneManager;
        this.match = match;
        this.view = new MatchViewImpl(sceneManager, this, boardView);
    }

    @Override
    public void startMatch() {
        this.view.show();
        this.boardView.updatePlayerPositions(
                this.boardController.getPlayerPositions(),
                null,
                List.of(),
                null,
                null);
        this.view.showCurrentTurn(this.match.getCurrentPlayer().getNickName()
                        + " (" + this.match.getCurrentPlayer().getPiece().getColor() + ")");
        this.view.showMessage("La partita e' iniziata - Gioca: "
                + this.match.getCurrentPlayer().getNickName()
                + " (" + this.match.getCurrentPlayer().getPiece().getColor() + ")");

    }

    @Override
    public int getDiceNumber() {
        return this.match.getDiceNumber();
    }

    @Override
    public void rollDice() {
        if (this.match.isGameOver()) {
            return;
        }
        final Player currentPlayer = this.match.getCurrentPlayer();

        if (currentPlayer.isInPrison()) {
            currentPlayer.setInPrison(false);
            SoundManager.getInstance().playSfx(SoundEffect.PRISON_DOOR);
            this.view.showMessage(currentPlayer.getNickName() + " (" + currentPlayer.getPiece().getColor() + ") e' uscito di prigione");
            this.match.nextTurn();
            final Player nextPlayer = this.match.getCurrentPlayer();
            this.view.showCurrentTurn(nextPlayer.getNickName() + " (" + nextPlayer.getPiece().getColor() + ")");
            return;
        }
        final List<Integer> diceResult = this.match.rollDice();

        SoundManager.getInstance().playSfx(SoundEffect.DICE_ROLL);
        this.view.showDiceResult(diceResult);

        final PauseTransition pause = new PauseTransition(Duration.millis(1000));
        this.currentPause = pause;
        pause.setOnFinished(event -> {
            if (!this.isMatchActive) {
                return;
            }
            int totalSteps = 0;
            for (final int roll : diceResult) {
                totalSteps += roll;
            }
            this.match.moveCurrentPlayer(totalSteps);
            final List<Integer> movePositions = this.match.getLastMovePositions();
            final List<Integer> intermediatePositions = movePositions.size() > 1
                    ? movePositions.subList(0, movePositions.size() - 1)
                    : List.of();

            final Runnable onIntermediate = () -> {
                if (!this.isMatchActive) {
                    return;
                }
                SoundManager.getInstance().playSfx(SoundEffect.SPECIAL_CELL);
            };

            final Runnable onFinal = () -> {
                if (!this.isMatchActive) {
                    return;
                }
                for (final int pos : intermediatePositions) {
                    final int offset = this.boardController.getCellOffset(pos);
                    if (offset != 0) {
                        final String direzione = offset > 0 ? "avanti" : "indietro";
                        this.view.showMessage("Wow!!! " + currentPlayer.getNickName()
                                + " (" + currentPlayer.getPiece().getColor()
                                + ") e' finito nella cella speciale " + pos + "! "
                                + direzione + " di " + Math.abs(offset) + " caselle");
                    }
                }
                if (currentPlayer.isInPrison()) {
                    SoundManager.getInstance().playSfx(SoundEffect.PRISON_DOOR);
                    this.view.showMessage("Ops!!! " + currentPlayer.getNickName()
                            + " (" + currentPlayer.getPiece().getColor() + ") e' finito in prigione!");
                }
                if (this.match.isGameOver()) {
                    SoundManager.getInstance().playSfx(SoundEffect.WIN);
                    this.view.showWinner(this.match.getWinner().getNickName()
                            + " (" + this.match.getWinner().getPiece().getColor() + ")");
                } else {
                    this.match.nextTurn();
                    final Player nextPlayer = this.match.getCurrentPlayer();
                    this.view.showCurrentTurn(nextPlayer.getNickName()
                            + " (" + nextPlayer.getPiece().getColor() + ")");
                }
            };
            this.boardView.updatePlayerPositions(
                    this.boardController.getPlayerPositions(),
                    currentPlayer.getPiece().getColor(),
                    intermediatePositions,
                    onIntermediate,
                    onFinal);
        });
        pause.play();
    }

    @Override
    public void quitMatch() {
        this.isMatchActive = false;
        if (this.currentPause != null) {
            this.currentPause.stop();
        }
        this.boardView.stopAnimations();
        this.sceneManager.showMenu();
    }

}
