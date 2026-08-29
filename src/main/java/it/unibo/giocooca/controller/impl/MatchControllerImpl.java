package it.unibo.giocooca.controller.impl;

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

//TODO Aggiungere un delay tra il lancio del dado e lo spostamento della pedina
public class MatchControllerImpl implements MatchController {
    private final SceneManager sceneManager;
    private final Match match;
    private final MatchView view;
    private final BoardView boardView;
    private final BoardController boardController;

    /**
     * Controller della partita
     * @param sceneManager per gestire le schermate
     * @param match la partita in corso
     */

    public MatchControllerImpl(final SceneManager sceneManager, final Match match) {
        if(sceneManager == null){
            throw new IllegalArgumentException("SceneManager cannot be null");
        }
        if(match == null){
            throw new IllegalArgumentException("Match cannot be null");
        }
        this.boardController = new BoardControllerImpl(match.getBoard(), match.getPlayers());
        this.boardView = new BoardViewImpl(boardController);

        this.sceneManager = sceneManager;
        this.match = match;
        this.view = new MatchViewImpl(sceneManager, this, boardView);
    }
    @Override
    public void startMatch(){
        this.view.show();
        this.boardView.updatePlayerPositions(this.boardController.getPlayerPositions());
        this.view.showCurrentTurn(this.match.getCurrentPlayer().getNickName());
        this.view.showMessage("La partita è iniziata - Gioca: " + this.match.getCurrentPlayer().getNickName());

    }
    @Override
    public void rollDice() {
        if(this.match.isGameOver()){
            return;
        }
        final Player currentPlayer = this.match.getCurrentPlayer();
        
        if(currentPlayer.isInPrison()){
            currentPlayer.setInPrison(false);
            SoundManager.getInstance().playSfx(SoundEffect.PRISON_DOOR);
            this.view.showMessage(currentPlayer.getNickName() + " è uscito di prigione");
            this.boardView.updatePlayerPositions(this.boardController.getPlayerPositions());
            this.match.nextTurn();
            this.view.showCurrentTurn(this.match.getCurrentPlayer().getNickName());
            return;
        }
        final int diceResult = this.match.rollDice();
        SoundManager.getInstance().playSfx(SoundEffect.DICE_ROLL);
        this.view.showDiceResult(diceResult);

        final int currentPlayerPosition = this.match.getCurrentPlayer().getPosition();
        this.match.moveCurrentPlayer(diceResult);
        SoundManager.getInstance().playSfx(SoundEffect.PIECE_MOVE);
        final int afterPlayerPosition = this.match.getCurrentPlayer().getPosition();
        this.boardView.updatePlayerPositions(this.boardController.getPlayerPositions());

        if(this.match.getCurrentPlayer().isInPrison()){
            SoundManager.getInstance().playSfx(SoundEffect.PRISON);
            this.view.showMessage("Ops!!! " + this.match.getCurrentPlayer().getNickName() + " è finito in prigione!");
        }else if(afterPlayerPosition != currentPlayerPosition + diceResult){
            SoundManager.getInstance().playSfx(SoundEffect.SPECIAL_CELL);
            this.view.showMessage("Wow!!! " + this.match.getCurrentPlayer().getNickName() + " è finito in una cella speciale!");
        }

        if(this.match.isGameOver()){
            SoundManager.getInstance().playSfx(SoundEffect.WIN);
            this.view.showWinner(this.match.getWinner().getNickName());
        } else{
            this.match.nextTurn();
            this.view.showCurrentTurn(this.match.getCurrentPlayer().getNickName());
        }
    }
    
    @Override
    public void quitMatch() {
        this.sceneManager.showMenu();
    }


}
