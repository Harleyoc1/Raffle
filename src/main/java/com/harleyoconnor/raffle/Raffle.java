package com.harleyoconnor.raffle;

import com.harleyoconnor.javautilities.IntegerUtils;
import com.harleyoconnor.raffle.utils.BufferedInputUtils;

import java.util.*;

/**
 * @author Harley O'Connor
 */
public final class Raffle {

    public enum MenuItem {
        CHECK, PURCHASE, STOP_PROGRAM
    }

    /**
     * Stores the user's username and raffle ticket.
     */
    public Map<String, Integer> raffleTickets = new HashMap<>();

    /**
     * Does one menu cycle.
     *
     * @return True if user wants to cycle again.
     */
    public boolean menuCycle () {
        final List<String> menuItemStrings = new ArrayList<>();

        // Loop through each menu item enum and add it to the menu item strings in semi-formatted manor.
        Arrays.stream(MenuItem.values()).forEach(menuItem -> menuItemStrings.add(menuItem.toString().toLowerCase().replace("_", " ")));

        final String selection = BufferedInputUtils.getSelection("\nWelcome to the Raffle. Select an option by number from below.", menuItemStrings);
        final MenuItem menuItem = MenuItem.valueOf(selection.toUpperCase().replace(" ", "_")); // Grab the menu enum from their selection.

        return this.runFromMenuItem(menuItem);
    }

    /**
     * Runs relevant code depending on which menu item was selected, using a switch.
     *
     * @param menuItem The selected menu item.
     * @return True if user wants to cycle again.
     */
    private boolean runFromMenuItem (final MenuItem menuItem) {
        // If they're not stopping the program, store their name from input.
        final String name = menuItem == MenuItem.STOP_PROGRAM ? null : BufferedInputUtils.getInput("\nWhat is your name?");

        // Test condition of menu item.
        switch (menuItem) {
            case CHECK: // If it's on check, check their ticket.
                this.checkTicket(name);
                break;
            case PURCHASE: // Otherwise, if it's on purchase, generate them a ticket.
                this.purchaseTicket(name);
                break;
            case STOP_PROGRAM: // Otherwise, stop the program.
                System.out.println("\nThank you for using the raffle.");
                return false;
        }

        return true;
    }

    /**
     * Checks the user's ticket.
     *
     * @param name The name of the user.
     */
    private void checkTicket (final String name) {
        // Check they have bought a ticket.
        if (!this.raffleTickets.containsKey(name)) {
            System.out.println("\nYou haven't bought a ticket yet.");
            return;
        }

        // Get their ticket number.
        final int ticketNumberIn = BufferedInputUtils.getIntInput("What is your ticket number?");

        // Check their ticket number matches the one stored.
        if (!this.raffleTickets.get(name).equals(ticketNumberIn)) {
            System.out.println("\nYou don't own this ticket.");
            return;
        }

        // If the ticket number is prime, they win the raffle, otherwise they lose.
        if (!IntegerUtils.isPrime(ticketNumberIn)) System.out.println("\nYou lost the raffle.");
        else System.out.println("\nYou won the raffle!");

        // Remove them from the raffle ticket map.
        this.raffleTickets.remove(name);
    }

    /**
     * Purchases a ticket.
     *
     * @param name The name of the user.
     */
    private void purchaseTicket (final String name) {
        // Check they don't already have a ticket.
        if (this.raffleTickets.containsKey(name)) {
            System.out.println("\nYou already have a ticket. Please check it before purchasing another.");
            return;
        }

        Integer randomRaffleNumber;

        // Get random number until, generate new number if the number generated already exists in the raffle map.
        do {
            randomRaffleNumber = IntegerUtils.getRandomIntBetween(1, 500);
        } while (this.raffleTickets.values().stream().anyMatch(randomRaffleNumber::equals));

        // Save their name and the number of their ticket.
        this.raffleTickets.put(name, randomRaffleNumber);
        System.out.println("\nSuccessfully bought raffle ticket number " + randomRaffleNumber + ".");
    }

}
