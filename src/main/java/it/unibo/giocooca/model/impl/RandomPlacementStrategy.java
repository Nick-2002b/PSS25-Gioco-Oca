package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.PlacementStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Strategia che colloca celle speciali in modo randomico.
 */
public final class RandomPlacementStrategy implements PlacementStrategy {
    @Override
    public List<Integer> getSpecialCellPositions(
            final int size,
            final int numSpecialCells,
            final int prisonPosition,
            final Random random) {
        final List<Integer> freePos = new ArrayList<>();
        for (int pos = 2; pos < size; pos++) {
            if (pos != GameConfig.PRISON_POSITION) {
                freePos.add(pos);
            }
        }

        Collections.shuffle(freePos, random);
        return freePos.subList(0, numSpecialCells);
    }
}
