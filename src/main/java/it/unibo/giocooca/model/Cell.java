package it.unibo.giocooca.model;

public interface Cell {
    CellType getType();
    
    int getPosition();

    void applyEffect(Player player);
}
