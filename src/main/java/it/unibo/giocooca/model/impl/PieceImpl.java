package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Piece;

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
