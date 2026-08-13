package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Dice;

import java.util.Random;

/**
 * Implementazione di un dado a 6 facce.
 * Usa Random per generare valori in [1, 6].
 */
public class DiceImpl implements Dice {

    private final Random random;

    /**
     * Costruttore per uso in produzione: seed casuale diverso ad ogni avvio.
     */
    public DiceImpl() {
        this.random = new Random();
    }

    /**
     * Costruttore per uso nei test: seed controllato.
     */
    public DiceImpl(Random random) {
        this.random = random;
    }

    @Override
    public int roll() {
        return this.random.nextInt(6) + 1;
    }
}
