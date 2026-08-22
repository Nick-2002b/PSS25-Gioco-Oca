package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Dice;

import java.util.Random;

/**
 * Implementazione di un dado a 6 facce.
 * Usa Random per generare valori in [1, 6].
 */
public final class DiceImpl implements Dice {
    private static final int SIDES = 6;

    private final Random random;

    /**
     * Costruttore per uso in produzione: seed casuale diverso ad ogni avvio.
     */
    public DiceImpl() {
        this.random = new Random();
    }

    /**
     * Costruttore per uso nei test: seed controllato.
     *
     * @param random il generatore di numeri casuali da usare, con seed controllato
     */
    public DiceImpl(final Random random) {
        this.random = random;
    }

    /**
     * Lancia il dado.
     *
     * @return un valore casuale compreso tra 1 e 6
     */
    @Override
    public int roll() {
        return this.random.nextInt(SIDES) + 1;
    }
}
