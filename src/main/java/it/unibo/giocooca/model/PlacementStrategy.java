package it.unibo.giocooca.model;

import java.util.List;
import java.util.Random;

/**
 * Strategia per determinare le posizioni delle caselle speciali sul tabellone.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface PlacementStrategy {

    /**
     * Calcola le posizioni in cui piazzare le caselle speciali.
     *
     * @param size            numero totale di caselle di gioco
     * @param numSpecialCells quante caselle speciali posizionare
     * @param prisonPosition  posizione della prigione, da escludere
     * @param random          generatore di numeri casuali
     * @return lista di posizioni
     */
    List<Integer> getSpecialCellPositions(
            int size,
            int numSpecialCells,
            int prisonPosition,
            Random random);
}
