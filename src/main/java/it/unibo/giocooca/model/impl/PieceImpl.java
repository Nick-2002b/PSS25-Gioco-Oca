package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Piece;

/**
 * Implementazione immutabile di una pedina del gioco.
 *
 * @param name  il nome della pedina
 * @param color il colore della pedina
 */
public record PieceImpl(String name, String color) implements Piece {
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }
}
