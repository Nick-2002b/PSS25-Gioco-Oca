package it.unibo.giocooca.model.impl;
import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Cell;

import java.util.ArrayList;
import java.util.List;

public class BoardMock implements Board {
    
    private final int size;
    private final List<Cell> cells;

    /**
     * Costruttore crea il tabellone con caselle normali
     * @param size è il numero totale delle caselle
     */
    public BoardMock(int size){
        this.size = size;
        this.cells = new ArrayList<>();
        for (int i = 0; i < size; i++){
            final int position = i;
            this.cells.add(new Cell(){
                @Override
                public int getPosition(){
                    return position;
                }
                @Override
                public String getType(){
                    return "NORMAL";
                }
            });
        }
    }
    @Override
    public int getSize(){
        return this.size;
    }
    @Override
    public Cell getCell(int position){
        if (position < 0 || position >= this.size){
            throw new IndexOutOfBoundsException("Posizione fuori dal tabellone" + position);
        }
        return this.cells.get(position);
    }
    @Override
    public List<Cell> getAllCells(){
        return List.copyOf(this.cells);
    }
}
