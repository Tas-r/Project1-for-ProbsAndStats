import java.util.Random;

/**
 * Simulates the Monty Hall problem to demonstrate the statistical advantage
 * of switching doors
 */
class PlayGame {

    private static Random random = new Random(); // Random number generator for making choices

    /**
     * Simulates one round of the Monty Hall game
     *
     * @param switchDoor Whether the player switches their choice after a door is revealed
     * @return true if the player wins the prize, false otherwise
     */
    private static boolean playGame(boolean switchDoor) {
        // Create three doors, all initially without prizes
        Door[] doors = { new Door(false), new Door(false), new Door(false) };

        // Randomly place the prize behind one of the doors
        int prizeDoor = random.nextInt(3);
        doors[prizeDoor].hasPrize = true;

        // Player makes an initial random choice
        int playerChoice = random.nextInt(3);

        // Host reveals a door that doesn't have the prize and wasn't chosen by the player
        int revealedDoor;
        do {
            revealedDoor = random.nextInt(3);
        } while (revealedDoor == prizeDoor || revealedDoor == playerChoice);

        // If the player's strategy is to switch, they choose the remaining door
        if (switchDoor) {
            for (int i = 0; i < 3; i++) {
                if (i != playerChoice && i != revealedDoor) {
                    playerChoice = i;
                    break;
                }
            }
        }

        // Return whether the player's final choice has the prize
        return doors[playerChoice].hasPrize;
    }

    /**
     * Runs multiple simulations of the Monty Hall game to calculate win percentage
     *
     * @param switchDoor Whether the player switches their door choice
     * @param trials Number of games to simulate
     * @return The percentage of games won using the specified strategy
     */
    public static double simulate(boolean switchDoor, int trials) {
        int wins = 0;

        // Run the specified number of trials
        for (int i = 0; i < trials; i++) {
            if (playGame(switchDoor)) wins++;
        }

        // Calculate and return win percentage
        return (wins * 100.0) / trials;
    }
}