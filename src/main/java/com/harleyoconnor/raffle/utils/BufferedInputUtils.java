package com.harleyoconnor.raffle.utils;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Input class which uses a buffered reader for standard input.
 *
 * @author Harley O'Connor
 */
public final class BufferedInputUtils {

    /**
     * A buffered reader object setup to read standard input (System.in).
     */
    private static final BufferedReader STD_IN_READER = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Gets user input, or returns null if there was an error reading it.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @return The entered string, or null if error.
     */
    public static String getInput (final String prompt) {
        return getInput(prompt, true);
    }

    /**
     * Gets user input, or returns null if there was an error reading it.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @param requireNotEmpty A boolean value stating whether or not the string must be set a value.
     * @return The entered string, or null if error.
     */
    public static String getInput (final String prompt, final boolean requireNotEmpty) {
        return getInput(prompt, requireNotEmpty, false);
    }

    /**
     * Gets user input, or returns null if there was an error reading it.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @param requireNotEmpty A boolean value stating whether or not the string must be set a value.
     * @param printIfError A boolean value stating whether or not to print the error if there was one. Set to true if debugging an error.
     * @return The entered string, or null if error.
     */
    @Nullable
    public static String getInput (final String prompt, final boolean requireNotEmpty, final boolean printIfError) {
        String input;

        while (true) {
            System.out.print(prompt + " ");

            try {
                input = STD_IN_READER.readLine(); // Read line from standard input.
            } catch (IOException e) {
                if (printIfError) e.printStackTrace();
                return null;
            }

            // Break if requireNotEmpty is false or the input is empty.
            if (!requireNotEmpty || !input.replaceAll(" ", "").equals("")) break;

            System.out.println("Please enter a valid string.");
        }

        return input;
    }

    /**
     * Gets a non-zero, positive integer input, or returns null if there was an error.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @return The non-zero, positive integer input, or null if error.
     */
    @Nullable
    public static Integer getIntInput (final String prompt) {
        return getIntInput(prompt, true, true);
    }

    /**
     * Gets a non-zero integer input, or returns null if there was an error.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @param requirePositive A boolean value stating whether or not to allow negative values.
     * @return The non-zero integer input, or null if error.
     */
    @Nullable
    public static Integer getIntInput (final String prompt, final boolean requirePositive) {
        return getIntInput(prompt, requirePositive, true);
    }

    /**
     * Gets an integer input, or returns null if there was an error.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @param requirePositive A boolean value stating whether or not to allow negative values.
     * @param requireNotZero A boolean value stating whether or not to allow zero.
     * @return The integer input, or null if error.
     */
    @Nullable
    public static Integer getIntInput (final String prompt, final boolean requirePositive, final boolean requireNotZero) {
        int input = 0;

        while (true) {
            boolean invalidInt = false;

            try {
                final String inputStr = getInput(prompt);

                if (inputStr == null) return null;

                input = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                invalidInt = true;
            }

            if (invalidInt) {
                System.out.println("\nYou must enter a valid integer.");
                continue;
            }

            if ((requirePositive && input < 0) || (requireNotZero && input == 0)) {
                System.out.println("\nYou must enter a " + (requireNotZero ? "non-zero integer" : "") + (requireNotZero && requirePositive ? " that is positive" : "") + (requirePositive && !requireNotZero ? "positive integer" : "") + ".");
                continue;
            }

            break;
        }

        return input;
    }

    /**
     * Presents the user with all the options in the possible selections list, returns the one they select.
     *
     * @param prompt The message sent to the user informing them of what they are selecting. Note that the possible selections are added to this within the method.
     * @param possibleSelections The list of possible selection strings.
     * @return The possible selection string inputted.
     */
    public static String getSelection (String prompt, final List<String> possibleSelections) {
        final StringBuilder promptBuilder = new StringBuilder(prompt);

        for (int i = 1; i <= possibleSelections.size(); i++)
            promptBuilder.append("\n").append(i).append(". ").append(possibleSelections.get(i - 1).toLowerCase());

        promptBuilder.append("\n> ");
        prompt = promptBuilder.toString();

        while (true) {
            final int selectionIndex = getIntInput(prompt, true, true) - 1;
            String selection = "";

            try {
                selection = possibleSelections.get(selectionIndex);
            } catch (IndexOutOfBoundsException ignored) { }

            if (selection.equals("")) {
                System.out.println("\nPlease enter a valid selection index.");
                continue;
            }

            return selection;
        }
    }

}
