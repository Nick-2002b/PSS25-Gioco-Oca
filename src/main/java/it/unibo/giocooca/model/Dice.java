package it.unibo.giocooca.model;

/**
 * Contratto che rappresenta il dado.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface Dice {
    /**
     * Lancia il dado.
     *
     * @return il valore ottenuto dal lancio
     */
    int roll();

}
