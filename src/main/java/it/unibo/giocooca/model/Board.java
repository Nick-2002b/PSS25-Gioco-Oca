package it.unibo.giocooca.model;

import java.util.List;

public interface Board {
    Cell getCell(int position);
    List<Cell> getAllCells();
}
