package it.unibo.giocooca;

import it.unibo.giocooca.model.impl.DiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DiceImplTest {
    @RepeatedTest(20)
    void rollShouldAlwaysBeInRange(){
        final DiceImpl dice = new DiceImpl();
        final int result = dice.roll();
        Assertions.assertTrue(result >= 1 && result <= 6, "Dice roll result should be between 1 and 6, but was: " + result);
    }

    @Test
    void rollWithFixedSeedShouldBeDeterministic(){
        final long seed = 42L;
        final DiceImpl dice1 = new DiceImpl(new Random(seed));
        final DiceImpl dice2 = new DiceImpl(new Random(seed));

        Assertions.assertEquals(dice1.roll(), dice2.roll());
    }
}
