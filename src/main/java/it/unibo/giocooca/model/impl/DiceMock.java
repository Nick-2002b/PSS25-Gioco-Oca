package it.unibo.giocooca.model.impl;
import it.unibo.giocooca.model.Dice;
import java.util.Random;

public class DiceMock implements Dice{
    private final Random random = new Random();
    private int result = 1;

    @Override
    public int roll() {
        this.result = random.nextInt(6) + 1;
        return this.result;
    }

}
