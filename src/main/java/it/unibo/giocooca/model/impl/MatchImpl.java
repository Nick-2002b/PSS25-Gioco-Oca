package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Match;

import it.unibo.giocooca.model.Dice;

import it.unibo.giocooca.model.Player;

import it.unibo.giocooca.model.Board;

import it.unibo.giocooca.model.Cell;

import java.util.List;

import java.util.ArrayList;

import java.util.Collections;

import java.util.HashSet;

import java.util.Set;

/**
 * Implementazione di una partita del gioco dell'oca.
 */
public final class MatchImpl implements Match {

    private final List<Player> players;
    private final Board board;
    private final List<Dice> dice;
    private int currentPlayerIndex;
    private boolean gameOver;
    private Player winner;
    private List<Integer> lastMovePositions;

    /**
     * Costruttore.
     *
     * @param players lista dei giocatori
     * @param board   tabellone da gioco
     * @param dice    dado
     */
    public MatchImpl(final List<Player> players, final Board board, final List<Dice> dice) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Almeno un giocatore");
        }
        if (board == null) {
            throw new IllegalArgumentException("Board non può essere null");
        }
        if (dice == null || dice.isEmpty()) {
            throw new IllegalArgumentException("Dice non può essere null");
        }
        this.players = List.copyOf(players);
        this.dice = List.copyOf(dice);
        this.board = board;
        this.currentPlayerIndex = 0;
        this.gameOver = false;
        this.winner = null;
        this.lastMovePositions = new ArrayList<>();
    }

    /**
     * Costruttore con un solo dado utilizzato nei test (lo teniamo per non rompere il test)
     */
    public MatchImpl(final List<Player> players, final Board board, final Dice singleDice) {
        this(players, board, List.of(singleDice));
    }

    @Override
    public int getDiceNumber() {
        return this.dice.size();
    }

    @Override
    public List<Integer> rollDice() {
        final List<Integer> results = new ArrayList<>();
        for (final Dice d : this.dice) {
            results.add(d.roll());
        }
        return results;
    }

    @Override
    public List<Integer> getLastMovePositions() {
        return Collections.unmodifiableList(this.lastMovePositions);
    }

    @Override
    public void moveCurrentPlayer(final int steps) {
        if (this.gameOver) {
            return;
        }
        final Player currentPlayer = getCurrentPlayer();
        int newPosition = currentPlayer.getPosition() + steps;
        final int endPosition = this.board.getSize() - 1;
        this.lastMovePositions = new ArrayList<>();
        if (newPosition >= endPosition) {
            newPosition = endPosition;
            this.gameOver = true;
            this.winner = currentPlayer;
            currentPlayer.setPosition(newPosition);
            this.lastMovePositions.add(newPosition);
            return;
        }
        currentPlayer.setPosition(newPosition);
        this.lastMovePositions.add(newPosition);
        applyCurrentCellEffect(currentPlayer);
    }

    @Override
    public void applyCurrentCellEffect(final Player player) {
        if (this.gameOver) {
            return;
        }
        final Set<Integer> visitedPositions = new HashSet<>();
        final int endPosition = this.board.getSize() - 1;
        if (this.lastMovePositions == null) {
            this.lastMovePositions = new ArrayList<>();
        }
        while (!this.gameOver) {
            final int playerPos = player.getPosition();
            if (visitedPositions.contains(playerPos)) {
                break;
            }
            visitedPositions.add(playerPos);
            final Cell cell = this.board.getCell(playerPos);
            cell.applyEffect(player);
            final int newPos = player.getPosition();
            if (newPos >= endPosition) {
                player.setPosition(endPosition);
                this.gameOver = true;
                this.winner = player;
                this.lastMovePositions.add(endPosition);
                break;
            }
            if (newPos == playerPos) {
                break;
            }
            this.lastMovePositions.add(newPos);
        }
    }

    @Override
    public void nextTurn() {
        if (!this.gameOver) {
            this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        }
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayerIndex);
    }

    @Override
    public Player getWinner() {
        return this.winner;
    }

    @Override
    public List<Player> getPlayers() {
        return this.players;

    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
