package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.PlacementStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Strategia che colloca celle speciali a intervalli fissi.
 */
public final class FixedFrequencyPlacementStrategy implements PlacementStrategy {
    @Override
    public List<Integer> getSpecialCellPositions(
            final int size,
            final int numSpecialCells,
            final int prisonPosition,
            final Random random) {

        final List<Integer> validPositions = new ArrayList<>();

        for (int pos = 2; pos < size; pos++) {
            if (pos != prisonPosition) {
                validPositions.add(pos);
            }
        }

        final double step = (double) validPositions.size() / numSpecialCells;

        final List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < numSpecialCells; i++) {
            positions.add(validPositions.get((int) (i * step)));
        }

        return positions;
    }
}
