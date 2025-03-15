import java.util.*;

/**
 * Represents a Pokemon Trading Card Game simulation
 * Used for calculating probabilities of certain game scenarios
 */
class PokemonGame {
    // Game zones where cards can be placed
    List<Card> deck = new ArrayList<>();    // Main deck of cards
    List<Card> hand = new ArrayList<>();    // Cards in player's hand
    List<Card> discard = new ArrayList<>(); // Discard pile
    List<Card> prize = new ArrayList<>();   // Prize cards set aside at game start
    List<Card> bench = new ArrayList<>();   // Pokemon on the bench

    Random random = new Random(); // For shuffling deck

    /**
     * Creates and shuffles a deck with specified card counts
     *
     * @param pokemonCount Number of Pokemon cards to include
     * @param energyCount Number of Energy cards to include
     * @param trainerCount Number of Trainer cards to include
     */
    void setDeck(int pokemonCount, int energyCount, int trainerCount) {
        deck.clear(); // Start with an empty deck

        // Add Pokemon cards
        for (int i = 0; i < pokemonCount; i++) {
            deck.add(new Pokemon("Charmander", 50, "Scratch", 10, "Water"));
        }

        // Add Energy cards
        for (int i = 0; i < energyCount; i++) {
            deck.add(new Energy("Fire"));
        }

        // Add Trainer cards (specifically Rare Candy)
        for (int i = 0; i < trainerCount; i++) {
            deck.add(new Trainer("Rare Candy", "Lets pokemon evolve"));
        }

        Collections.shuffle(deck); // Randomize card order
    }

    /**
     * Draw multiple cards from deck to hand
     *
     * @param count Number of cards to draw
     */
    void drawCard(int count) {
        for (int i = 0; i < count; i++) {
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0)); // Take top card from deck and add to hand
            }
        }
    }

    /**
     * Check if player has at least one Pokemon card in hand
     * In the Pokemon TCG, a hand with no Pokemon is a "mulligan"
     *
     * @return true if hand contains at least one Pokemon card
     */
    boolean hasPokemonInHand() {
        for (Card card : hand) {
            if (card instanceof Pokemon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Set aside prize cards at the beginning of the game
     * In Pokemon TCG, typically 6 cards are set aside as prizes
     *
     * @param count Number of prize cards to set aside
     */
    void setPrize(int count) {
        prize.clear(); // Start with empty prize pile
        for (int i = 0; i < count; i++) {
            prize.add(deck.remove(0)); // Take top card from deck as prize
        }
    }

    /**
     * Checks if all Rare Candy cards are in the prize pile
     * This is known as "bricking" - when key cards are inaccessible
     *
     * @param rareCandyCount Total number of Rare Candy cards in the deck
     * @return true if all Rare Candy cards are in the prize pile
     */
    boolean checkBrick(int rareCandyCount) {
        int rareCandyInPrize = 0;
        for (Card card : prize) {
            if (card instanceof Trainer && ((Trainer) card).name.equals("Rare Candy")) {
                rareCandyInPrize++;
            }
        }
        return rareCandyInPrize == rareCandyCount; // if all are in the prize pile, return true (brick)
    }

    /**
     * Simulates many games to determine probability of "bricking"
     * where all Rare Candy cards end up in the prize pile
     *
     * @param trials Number of games to simulate for statistical significance
     */
    static void simulateBrickOdds(int trials) {
        System.out.println("Rare Candy Count, Brick %"); // print header for results

        // try different numbers of Rare Candy in the deck (from 1 to 4)
        for (int rareCandyCount = 1; rareCandyCount <= 4; rareCandyCount++) {
            int bricks = 0; // keeps track of how many times the player "bricks"

            for (int t = 0; t < trials; t++) { // repeat this test multiple times
                PokemonGame game = new PokemonGame(); // create a new game instance

                // Create a deck with 10 Pokémon, some Rare Candy, and the rest energy
                // Total deck size is 60 cards (standard Pokemon TCG deck size)
                game.setDeck(10, 50 - rareCandyCount, rareCandyCount);

                // In Pokemon TCG, if you have no Pokemon in your starting hand,
                // you must redraw (mulligan). This loop simulates that rule
                do {
                    game.hand.clear();
                    game.drawCard(7); // draw the starting 7 cards
                } while (!game.hasPokemonInHand());

                game.setPrize(6); // set aside 6 prize cards

                // Check if all Rare Candy cards ended up in the prize pile
                if (game.checkBrick(rareCandyCount)) {
                    bricks++;
                }
            }

            // calculate the percentage of "bricks" and print the results
            double brickRate = (bricks / (double) trials) * 100;
            System.out.println(rareCandyCount + ", " + brickRate);
        }
    }

    /**
     * Calculates probability of getting a mulligan (no Pokemon in hand)
     * for different Pokemon counts in the deck
     *
     * @param trials Number of hands to draw for each Pokemon count
     */
    static void calcMulliganPercent(int trials) {
        System.out.println("Pokemon Count, Mulligan %");

        // Test with different numbers of Pokemon in the deck (1 to 60)
        for (int pokemonCount = 1; pokemonCount <= 60; pokemonCount++) {
            int mulligan = 0; // Counter for hands with no Pokemon

            for (int i = 0; i < trials; i++) {
                PokemonGame game = new PokemonGame();
                // Create deck with varying Pokemon count, rest as energy, no trainers
                game.setDeck(pokemonCount, 60 - pokemonCount, 0);
                game.drawCard(7); // Draw initial 7-card hand

                // Count as mulligan if no Pokemon in hand
                if (!game.hasPokemonInHand()) {
                    mulligan++;
                }
            }

            // Calculate and print mulligan percentage
            double mulliganRate = (mulligan / (double) trials) * 100;
            System.out.println(pokemonCount + " , " + mulliganRate);
        }
    }
}