package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.controller.BoardController;
import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementazione del controller del tabellone di gioco.
 */
public final class BoardControllerImpl implements BoardController {
    private final Board board;
    private final List<Player> players;

    /**
     * Crea il controller del tabellone.
     *
     * @param board   il tabellone di gioco
     * @param players la lista dei giocatori della partita
     */
    public BoardControllerImpl(final Board board, final List<Player> players){
        this.board = board;
        this.players = players;
    }

    @Override
    public int getBoardSize() {
        return this.board.getSize();
    }

    @Override
    public String getCellType(int position) {
        return this.board.getCell(position).getType().name();
    }

    @Override
    public Map<String, Integer> getPlayerPositions() {
        final Map<String, Integer> positions = new HashMap<>();
        for (final Player player : this.players) {
            if (player.getPosition() >= 1) {
                positions.put(player.getPiece().getColor(), player.getPosition());
            }
        }
        return positions;
    }
}
