package it.unibo.giocooca.model.impl;

import java.util.Random;

import it.unibo.giocooca.model.Dice;

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
     * @return un valore casuale compreso tra 1 e il numero delle facce (6)
     */
    @Override
    public int roll() {
        return this.random.nextInt(SIDES) + 1;
    }
}
