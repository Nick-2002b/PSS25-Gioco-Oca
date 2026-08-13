package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Cell;
import it.unibo.giocooca.model.CellType;
import it.unibo.giocooca.model.Player;

public class NormalCellImpl implements Cell {
    private final int position;

    public NormalCellImpl(int position) {
        this.position = position;
    }

    @Override
    public CellType getType() {
        return CellType.NORMAL;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void applyEffect(Player player) {
        // None effect
    }
}
