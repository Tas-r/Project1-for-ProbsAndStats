import java.util.*;


class PokemonGame {
    // Game zones where cards can be placed
    List<Card> deck = new ArrayList<>();    // Main deck of cards
    List<Card> hand = new ArrayList<>();    // Cards in player's hand
    List<Card> discard = new ArrayList<>(); // Discard pile
    List<Card> prize = new ArrayList<>();   // Prize cards set aside at game start
    List<Card> bench = new ArrayList<>();   // Pokemon on the bench

    Random random = new Random(); // For shuffling deck


     // Creates and shuffles a deck with specified card counts

    void InitializeDeck(int pokemonCount, int energyCount, int trainerCount) {
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


      //Drawing multiple cards from deck to hand

    void drawCard(int count) {
        for (int i = 0; i < count; i++) {
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0)); // Take top card from deck and add to hand
            }
        }
    }

   // if there is a pokemon in the hand, return true
    boolean hasPokemonInHand() {
        for (Card card : hand) {
            if (card instanceof Pokemon) {
                return true;
            }
        }
        return false;
    }

    //set prize cards aside at the start of the game
    void setPrize(int count) {
        prize.clear(); // Start with empty prize pile
        for (int i = 0; i < count; i++) {
            prize.add(deck.remove(0)); // Take top card from deck as prize
        }
    }

    //check if all rare candy cards are in the prize pile
    boolean checkBrick(int rareCandyCount) {
        int rareCandyInPrize = 0;
        for (Card card : prize) {
            if (card instanceof Trainer && ((Trainer) card).name.equals("Rare Candy")) {
                rareCandyInPrize++;
            }
        }
        return rareCandyInPrize == rareCandyCount; // if all are in the prize pile, return true (brick)
    }


    static void simulateBrickOdds(int trials) {
        System.out.println("Rare Candy Count, Brick %"); // print header for results

        // try different numbers of Rare Candy in the deck (from 1 to 4)
        for (int rareCandyCount = 1; rareCandyCount <= 4; rareCandyCount++) {
            int bricks = 0; // keeps track of how many times the player "bricks"

            for (int t = 0; t < trials; t++) { // repeat this test multiple times
                PokemonGame game = new PokemonGame(); // create a new game instance

                // Create a deck with 10 Pokémon, some Rare Candy, and the rest energy
                // Total deck size is 60 cards (standard Pokemon TCG deck size)
                game.InitializeDeck(10, 50 - rareCandyCount, rareCandyCount);

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

    // Calculates probability of getting a mulligan or no Pokemon in hand
    static void calcMulliganPercent(int trials) {
        System.out.println("Pokemon Count, Mulligan %");

        // Test with different numbers of Pokemon in the deck (1 to 60)
        for (int pokemonCount = 1; pokemonCount <= 60; pokemonCount++) {
            int mulligan = 0; // Counter for hands with no Pokemon

            for (int i = 0; i < trials; i++) {
                PokemonGame game = new PokemonGame();
                // Deck with varying Pokemon count, rest as energy, no trainers
                game.InitializeDeck(pokemonCount, 60 - pokemonCount, 0);
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