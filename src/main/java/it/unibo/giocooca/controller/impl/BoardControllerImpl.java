package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.controller.BoardController;

import java.util.Map;

public class BoardControllerImpl implements BoardController {

    @Override
    public int getBoardSize() {
        return 0;
    }

    @Override
    public String getCellType(int position) {
        return "";
    }

    @Override
    public Map<String, Integer> getPlayerPositions() {
        return Map.of();
    }
}
