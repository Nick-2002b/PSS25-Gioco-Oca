package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.PlacementStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FixedFrequencyPlacementStrategy implements PlacementStrategy {
    @Override
    public List<Integer> getSpecialCellPositions(int size, int numSpecialCells, int prisonPosition, Random random) {

        final List<Integer> validPositions = new ArrayList<>();

        for (int pos = 2; pos < size; pos++) {
            if (pos != prisonPosition) {
                validPositions.add(pos);
            }
        }

        final double step = (double)validPositions.size() / numSpecialCells;

        final List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < numSpecialCells; i++) {
            positions.add(validPositions.get((int) (i * step)));
        }

        return positions;
    }
}
