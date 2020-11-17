package com.harleyoconnor.raffle;

/**
 * @author Harley O'Connor
 */
public final class Main {

    private Main () {}

    public static void main (final String[] args) {
        final Raffle raffle = new Raffle(); // Create the raffle object.
        boolean loopAgain;

        // Do a menu cycle until the user decides to stop the program.
        do {
            loopAgain = raffle.menuCycle();
        } while (loopAgain);
    }

}
