import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class DealorNoDeal {
    private ArrayList<Double> cases; // List to hold the money for each case
    private Random random;           // Random number generator for simulation

    // Constructor: Initializes random generator
    public DealorNoDeal() {
        random = new Random();
    }

    // Initializes the cases with prize amounts and shuffles them
    private void initializeCases() {
        cases = new ArrayList<>();
        double[] amounts = {0.01, 1, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750,
                1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000,
                300000, 400000, 500000, 750000, 1000000};
        for (double amount : amounts) {
            cases.add(amount);
        }
        Collections.shuffle(cases, random); // Shuffle with Random instance
    }

    // Picks a random initial case for the player
    private double pickPlayerCase() {
        int playerCase = random.nextInt(26); // Randomly pick a case (0-25)
        double playerCaseValue = cases.get(playerCase); // Store its value
        cases.set(playerCase, null); // Mark as taken
        return playerCaseValue;
    }

    // Opens a specified number of cases randomly
    private void openCases(int numToOpen) {
        int opened = 0;
        while (opened < numToOpen && cases.stream().anyMatch(amount -> amount != null)) {
            int choice;
            do {
                choice = random.nextInt(26); // Random case index (0-25)
            } while (cases.get(choice) == null); // Ensure it’s not already opened
            cases.set(choice, null); // Open the case
            opened++;
        }
    }

    // Gets the value of the last remaining case
    private double getLastRemainingCaseValue() {
        for (Double amount : cases) {
            if (amount != null) {
                return amount; // Return the only unopened case value
            }
        }
        return 0; // Shouldn’t happen with proper logic
    }

    // Simulates one full game and returns outcomes for keep vs. switch
    private double[] simulateGame() {
        initializeCases(); // Reset and shuffle cases
        double playerCaseValue = pickPlayerCase(); // Player picks a case
        int casesToOpenPerRound = 6;

        // Simulate rounds until one case remains
        while (cases.stream().filter(amount -> amount != null).count() > 1) {
            openCases(casesToOpenPerRound);
            if (casesToOpenPerRound > 1) {
                casesToOpenPerRound--; // Decrease cases to open per round
            }
        }

        double lastCaseValue = getLastRemainingCaseValue(); // Value of the last case

        // Return [keep outcome, switch outcome]
        return new double[]{playerCaseValue, lastCaseValue};
    }

    // Run the Monte Carlo simulation
    public void runSimulation(int trials) {
        double totalKeepWinnings = 0;
        double totalSwitchWinnings = 0;
        int keepWins = 0;  // Count when keeping beats switching
        int switchWins = 0; // Count when switching beats keeping

        for (int i = 0; i < trials; i++) {
            double[] outcomes = simulateGame();
            double keepValue = outcomes[0];  // Value if player keeps original case
            double switchValue = outcomes[1]; // Value if player switches to last case

            totalKeepWinnings += keepValue;
            totalSwitchWinnings += switchValue;

            if (keepValue > switchValue) {
                keepWins++;
            } else if (switchValue > keepValue) {
                switchWins++;
            }
        }

        // Calculate and display results
        double avgKeepWinnings = totalKeepWinnings / trials;
        double avgSwitchWinnings = totalSwitchWinnings / trials;
        double keepWinRate = (double) keepWins / trials * 100;
        double switchWinRate = (double) switchWins / trials * 100;

        System.out.printf("Deal or No Deal Simulation Results:\n", trials);
        System.out.printf("Average winnings (Keep): $%.2f\n", avgKeepWinnings);
        System.out.printf("Average winnings (Switch): $%.2f\n", avgSwitchWinnings);
        System.out.printf("Win rate (Keep beats Switch): %.2f%%\n", keepWinRate);
        System.out.printf("Win rate (Switch beats Keep): %.2f%%\n", switchWinRate);
    }


}
