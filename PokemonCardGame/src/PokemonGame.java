import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


class PokemonGame {
    //deck, hand, prize, discard, bench arraylists for both players
    private ArrayList<Card> deckPlayer1, deckPlayer2;
    private ArrayList<Card> handPlayer1, handPlayer2;
    private ArrayList<Card> prizesPlayer1, prizesPlayer2;
    private ArrayList<Card> discardPlayer1, discardPlayer2;
    private ArrayList<Pokemon> benchPlayer1, benchPlayer2;
    private Pokemon activePlayer1, activePlayer2;
    private int prizeCountPlayer1 = 6, prizeCountPlayer2 = 6;
    private Random random = new Random();
    private boolean player1Turn = true;
    private boolean gameWon = false;
    private Scanner scanner = new Scanner(System.in);
    private boolean trainerPlayedThisTurn = false;
    private boolean ProfessorElmsAdviceActivePlayer1 = false;
    private boolean ProfessorElmsAdviceActivePlayer2 = false;

    public PokemonGame() {
        deckPlayer1 = new ArrayList<>();
        deckPlayer2 = new ArrayList<>();
        handPlayer1 = new ArrayList<>();
        handPlayer2 = new ArrayList<>();
        prizesPlayer1 = new ArrayList<>();
        prizesPlayer2 = new ArrayList<>();
        discardPlayer1 = new ArrayList<>();
        discardPlayer2 = new ArrayList<>();
        benchPlayer1 = new ArrayList<>();
        benchPlayer2 = new ArrayList<>();
        initializeDecks();
    }

    private void initializeDecks() {
        for (int i = 0; i < 4; i++) {
            deckPlayer1.add(new Pikachu());
            deckPlayer1.add(new Squirtle());
            deckPlayer1.add(new Charmander());
            deckPlayer2.add(new Pikachu());
            deckPlayer2.add(new Squirtle());
            deckPlayer2.add(new Charmander());
            deckPlayer1.add(new Torchic());
            deckPlayer2.add(new Torchic());
            deckPlayer1.add(new Shinx());
            deckPlayer2.add(new Shinx());
        }
        for (int i = 0; i < 4; i++) {
            deckPlayer1.add(new Hero());
            deckPlayer2.add(new Hero());
            deckPlayer1.add(new ProfessorElmsAdvice());
            deckPlayer2.add(new ProfessorElmsAdvice());
            deckPlayer1.add(new Bill());
            deckPlayer1.add(new ProfessorsResearch());
            deckPlayer2.add(new Bill());
            deckPlayer2.add(new ProfessorsResearch());

        }
        for (int i = 0; i < 5 ; i++) {
            deckPlayer1.add(new Electric());
            deckPlayer1.add(new Water());
            deckPlayer1.add(new Fire());
            deckPlayer2.add(new Electric());
            deckPlayer2.add(new Water());
            deckPlayer2.add(new Fire());

        }

        for (int i = 0; i < 9; i++) {
            deckPlayer1.add(new BasicEnergy());
            deckPlayer2.add(new BasicEnergy());
        }

        Collections.shuffle(deckPlayer1);
        Collections.shuffle(deckPlayer2);
    }

    private String getCurrentPlayer() {
        return player1Turn ? "Player 1" : "Player 2";
    }

    private String getOpponentPlayer() {
        return player1Turn ? "Player 2" : "Player 1";
    }

    private String getCoinFlipResult(int flip) {
        return flip == 0 ? "Heads" : "Tails";
    }

    private String getPokemonStatus(Pokemon pokemon) {
        if (pokemon == null) return "None";
        return pokemon.getName() + " (HP: " + pokemon.getHp() + ", Energy: " + pokemon.getEnergyAttached() + ")";
    }

    private void drawCard(ArrayList<Card> deck, ArrayList<Card> hand, int count) {
        if (deck.isEmpty()) {
            System.out.println("Deck is empty, cannot draw cards.");
            return;
        }
        for (int i = 0; i < count && !deck.isEmpty(); i++) {
            hand.add(deck.remove(0));
        }
    }

    private boolean determineIfPokemonInHand(ArrayList<Card> hand) {
        return hand.stream().anyMatch(card -> card instanceof Pokemon);
    }

    private void fillHand(ArrayList<Card> deck, ArrayList<Card> hand, ArrayList<Card> returnDeck,
                          ArrayList<Card> opponentDeck, ArrayList<Card> opponentHand) {
        drawCard(deck, hand, 7);

        while (!determineIfPokemonInHand(hand) && !deck.isEmpty()) {
            System.out.println("No Pokémon in hand. Reshuffling...");
            returnDeck.addAll(hand);
            Collections.shuffle(returnDeck);
            hand.clear();
            drawCard(deck, hand, 7);
            if (!determineIfPokemonInHand(hand)) {
                drawCard(opponentDeck, opponentHand, 1);
                System.out.println("Opponent draws 1 card because no Pokémon in hand!");
            }
        }
    }

    private void fillPrize(ArrayList<Card> deck, ArrayList<Card> prizes) {
        drawCard(deck, prizes, 6);
    }

    private void setupGame() {
        int flip = random.nextInt(2);
        System.out.println("Coin flip result: " + getCoinFlipResult(flip));
        player1Turn = (flip == 0);
        System.out.println(getCurrentPlayer() + " goes first!");

        fillHand(deckPlayer1, handPlayer1, deckPlayer1, deckPlayer2, handPlayer2);
        fillHand(deckPlayer2, handPlayer2, deckPlayer2, deckPlayer1, handPlayer1);
        fillPrize(deckPlayer1, prizesPlayer1);
        fillPrize(deckPlayer2, prizesPlayer2);

        // Player 1 selects active Pokémon (mandatory)
        System.out.println("Player 1, select your active Pokémon from your hand:");
        ArrayList<Integer> pokemonIndices1 = new ArrayList<>();
        for (int i = 0; i < handPlayer1.size(); i++) {
            if (handPlayer1.get(i) instanceof Pokemon) {
                pokemonIndices1.add(i);
                System.out.println(i + ": " + handPlayer1.get(i));
            }
        }
        int activeIndex1;
        do {
            activeIndex1 = scanner.nextInt();
            scanner.nextLine();
            if (!pokemonIndices1.contains(activeIndex1)) {
                System.out.println("Invalid selection! You must choose a Pokémon. Try again:");
            }
        } while (!pokemonIndices1.contains(activeIndex1));
        activePlayer1 = (Pokemon) handPlayer1.remove(activeIndex1);
        System.out.println("Player 1 set " + activePlayer1.getName() + " as active.");

        // Player 1 selects bench Pokémon (optional)
        while (!handPlayer1.isEmpty() && determineIfPokemonInHand(handPlayer1) && benchPlayer1.size() < 5) {
            System.out.println("Player 1, select a Pokémon to place on the bench (or -1 to skip):");
            pokemonIndices1.clear();
            for (int i = 0; i < handPlayer1.size(); i++) {
                if (handPlayer1.get(i) instanceof Pokemon) {
                    pokemonIndices1.add(i);
                    System.out.println(i + ": " + handPlayer1.get(i));
                }
            }
            int benchIndex1 = scanner.nextInt();
            scanner.nextLine();
            if (benchIndex1 == -1) break;
            if (pokemonIndices1.contains(benchIndex1)) {
                benchPlayer1.add((Pokemon) handPlayer1.remove(benchIndex1));
                System.out.println("Player 1 placed " + benchPlayer1.get(benchPlayer1.size()-1).getName() + " on bench.");
            } else {
                System.out.println("Invalid selection! Skipping bench placement.");
                break;
            }
        }

        // Player 2 selects active Pokémon (mandatory)
        System.out.println("Player 2, select your active Pokémon from your hand:");
        ArrayList<Integer> pokemonIndices2 = new ArrayList<>();
        for (int i = 0; i < handPlayer2.size(); i++) {
            if (handPlayer2.get(i) instanceof Pokemon) {
                pokemonIndices2.add(i);
                System.out.println(i + ": " + handPlayer2.get(i));
            }
        }
        int activeIndex2;
        do {
            activeIndex2 = scanner.nextInt();
            scanner.nextLine();
            if (!pokemonIndices2.contains(activeIndex2)) {
                System.out.println("Invalid selection! You must choose a Pokémon. Try again:");
            }
        } while (!pokemonIndices2.contains(activeIndex2));
        activePlayer2 = (Pokemon) handPlayer2.remove(activeIndex2);
        System.out.println("Player 2 set " + activePlayer2.getName() + " as active.");

        // Player 2 selects bench Pokémon (optional)
        while (!handPlayer2.isEmpty() && determineIfPokemonInHand(handPlayer2) && benchPlayer2.size() < 5) {
            System.out.println("Player 2, select a Pokémon to place on the bench (or -1 to skip):");
            pokemonIndices2.clear();
            for (int i = 0; i < handPlayer2.size(); i++) {
                if (handPlayer2.get(i) instanceof Pokemon) {
                    pokemonIndices2.add(i);
                    System.out.println(i + ": " + handPlayer2.get(i));
                }
            }
            int benchIndex2 = scanner.nextInt();
            scanner.nextLine();
            if (benchIndex2 == -1) break;
            if (pokemonIndices2.contains(benchIndex2)) {
                benchPlayer2.add((Pokemon) handPlayer2.remove(benchIndex2));
                System.out.println("Player 2 placed " + benchPlayer2.get(benchPlayer2.size()-1).getName() + " on bench.");
            } else {
                System.out.println("Invalid selection! Skipping bench placement.");
                break;
            }
        }

        printGameState(player1Turn);
    }

    private void printGameState(boolean isPlayer1) {
        System.out.println("\n=== Game State Update ===");
        System.out.println("Current Player: " + getCurrentPlayer());

        System.out.println("\nPlayer 1's State:");
        System.out.println("Hand: " + handPlayer1);
        System.out.println("Active Pokémon: " + getPokemonStatus(activePlayer1));
        System.out.println("Bench: " + benchPlayer1);
        System.out.println("Prizes left: " + prizesPlayer1.size());
        System.out.println("Deck size: " + deckPlayer1.size());
        System.out.println("Discard pile: " + discardPlayer1.size() + " cards");

        System.out.println("\nPlayer 2's State:");
        System.out.println("Hand: " + handPlayer2);
        System.out.println("Active Pokémon: " + getPokemonStatus(activePlayer2));
        System.out.println("Bench: " + benchPlayer2);
        System.out.println("Prizes left: " + prizesPlayer2.size());
        System.out.println("Deck size: " + deckPlayer2.size());
        System.out.println("Discard pile: " + discardPlayer2.size() + " cards");

        System.out.println("====================\n");
    }

    private boolean playerTurn(ArrayList<Card> deck, ArrayList<Card> hand, ArrayList<Card> prizes,
                               ArrayList<Card> returnDeck, ArrayList<Pokemon> bench, Pokemon active,
                               ArrayList<Card> opponentDeck, ArrayList<Card> opponentHand,
                               ArrayList<Card> opponentPrizes, Pokemon opponentActive) {
        System.out.println(getCurrentPlayer() + "'s turn:");
        printGameState(player1Turn);
        drawCard(deck, hand, 1);
        System.out.println("Drew 1 card. Hand: " + hand);

        boolean attacked = false;
        trainerPlayedThisTurn = false;
        boolean energyPlayedThisTurn = false;

        while (!attacked) {
            System.out.println("Choose action (1: Play Energy, 2: Play Pokémon, 3: Play Trainer, 4: Attack, 5: Retreat, 6: Pass):");
            try {
                int action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {
                    case 1:
                        if (!energyPlayedThisTurn && !hand.isEmpty()) {
                            System.out.println("Select Energy to attach:");
                            ArrayList<Integer> energyIndices = new ArrayList<>();
                            for (int i = 0; i < hand.size(); i++) {
                                if (hand.get(i) instanceof Energy) {
                                    energyIndices.add(i);
                                    System.out.println(i + ": " + hand.get(i));
                                }
                            }

                            if (energyIndices.isEmpty()) {
                                System.out.println("No Energy cards in hand!");
                                break;
                            }

                            int energyIndex = scanner.nextInt();
                            scanner.nextLine();

                            if (energyIndices.contains(energyIndex)) {
                                Energy energy = (Energy) hand.get(energyIndex);
                                String energyType = energy.getEnergyType();
                                String pokemonType = active.getPokeType();

                                boolean canAttach = false;
                                if (energyType.equals("Basic")) {
                                    canAttach = true;
                                } else if (energyType.equals("Electric") && pokemonType.equals("Electric")) {
                                    canAttach = true;
                                } else if (energyType.equals("Water") && pokemonType.equals("Water")) {
                                    canAttach = true;
                                } else if (energyType.equals("Fire") && pokemonType.equals("Fire")) {
                                    canAttach = true;
                                }

                                if (canAttach) {
                                    active.setEnergyAttached(active.getEnergyAttached() + 1);
                                    hand.remove(energyIndex);
                                    System.out.println("Attached " + energy.getEnergyType() + " Energy to " + active.getName());
                                    energyPlayedThisTurn = true;
                                } else {
                                    System.out.println("Cannot attach " + energy.getEnergyType() + " Energy to " +
                                            active.getName() + " - Type mismatch!");
                                }
                            } else {
                                System.out.println("Invalid Energy selection!");
                            }
                        } else {
                            System.out.println("Already played an Energy this turn or no Energy in hand!");
                        }
                        break;

                    case 2:
                        if (active == null) {
                            System.out.println("No active Pokémon - cannot place Pokémon on bench yet!");
                        } else if (bench.size() >= 5) {
                            System.out.println("Bench is full (max 5)!");
                        } else if (!hand.isEmpty() && determineIfPokemonInHand(hand)) {
                            System.out.println("Select Pokémon to place on bench:");
                            ArrayList<Integer> pokemonIndices = new ArrayList<>();
                            for (int i = 0; i < hand.size(); i++) {
                                if (hand.get(i) instanceof Pokemon) {
                                    pokemonIndices.add(i);
                                    System.out.println(i + ": " + hand.get(i));
                                }
                            }
                            int pokemonIndex = scanner.nextInt();
                            scanner.nextLine();
                            if (pokemonIndices.contains(pokemonIndex)) {
                                bench.add((Pokemon) hand.remove(pokemonIndex));
                                System.out.println("Placed " + bench.get(bench.size()-1).getName() + " on bench");
                            } else {
                                System.out.println("Invalid Pokémon selection!");
                            }
                        } else {
                            System.out.println("No Pokémon in hand to play!");
                        }
                        break;

                    case 3:
                        if (!trainerPlayedThisTurn && !hand.isEmpty()) {
                            System.out.println("Select Trainer to play:");
                            ArrayList<Integer> trainerIndices = new ArrayList<>();
                            for (int i = 0; i < hand.size(); i++) {
                                if (hand.get(i) instanceof Trainer) {
                                    trainerIndices.add(i);
                                    System.out.println(i + ": " + hand.get(i));
                                }
                            }

                            if (trainerIndices.isEmpty()) {
                                System.out.println("No Trainer cards in hand!");
                                break;
                            }

                            int trainerIndex = scanner.nextInt();
                            scanner.nextLine();

                            if (trainerIndices.contains(trainerIndex)) {
                                Trainer trainer = (Trainer) hand.remove(trainerIndex);
                                applyTrainerEffect(trainer, deck, hand, returnDeck);
                                trainerPlayedThisTurn = true;
                            } else {
                                System.out.println("Invalid Trainer selection!");
                            }
                        } else {
                            System.out.println("Already played a Trainer this turn or no Trainers in hand!");
                        }
                        break;

                    case 4:
                        if (active == null) {
                            System.out.println("No active Pokémon to attack with!");
                        } else if (active.getEnergyAttached() >= 1) {
                            int baseDamage = 10; // Base damage for the attack
                            int finalDamage = baseDamage;

                            // Check for Professor Elm's Advice effect
                            boolean professorElmEffectApplied = false;
                            if (player1Turn && ProfessorElmsAdviceActivePlayer1) {
                                finalDamage = baseDamage * 2;
                                ProfessorElmsAdviceActivePlayer1 = false; // Reset after use
                                professorElmEffectApplied = true;
                            } else if (!player1Turn && ProfessorElmsAdviceActivePlayer2) {
                                finalDamage = baseDamage * 2;
                                ProfessorElmsAdviceActivePlayer2 = false; // Reset after use
                                professorElmEffectApplied = true;
                            }

                            // Check for weakness after Professor Elm's Advice (multiplicative effect)
                            if (opponentActive.getWeakness().equals(active.getPokeType())) {
                                finalDamage *= 2; // Double damage again if weak
                            }

                            opponentActive.setHp(opponentActive.getHp() - finalDamage);
                            System.out.println(active.getName() + " attacked " + opponentActive.getName() + " for " + finalDamage + " damage!" +
                                    (professorElmEffectApplied ? " (Professor Elm's Advice applied)" : "") +
                                    (opponentActive.getWeakness().equals(active.getPokeType()) ? " (Weakness applied)" : ""));

                            // Discard energy after attack
                            int energyUsed = active.getEnergyAttached();
                            active.setEnergyAttached(0); // Reset energy to 0
                            if (player1Turn) {
                                for (int i = 0; i < energyUsed; i++) {
                                    discardPlayer1.add(new Energy("Generic")); // Add discarded energy to discard pile
                                }
                                System.out.println(active.getName() + " used its energy to attack. " + energyUsed + " energy discarded.");
                            } else {
                                for (int i = 0; i < energyUsed; i++) {
                                    discardPlayer2.add(new Energy("Generic")); // Add discarded energy to discard pile
                                }
                                System.out.println(active.getName() + " used its energy to attack. " + energyUsed + " energy discarded.");
                            }

                            if (opponentActive.getHp() <= 0) {
                                handleKnockout(hand, prizes, opponentActive);
                                if (player1Turn) {
                                    opponentActive = activePlayer2; // Player 2's new active Pokémon
                                } else {
                                    opponentActive = activePlayer1; // Player 1's new active Pokémon
                                }
                            }
                            attacked = true;
                        } else {
                            System.out.println("Not enough energy to attack! Attach new energy to attack again.");
                        }
                        break;

                    case 5:
                        if (active == null) {
                            System.out.println("No active Pokémon to retreat!");
                        } else if (active.getEnergyAttached() >= active.getRetreatCost() && !bench.isEmpty()) {
                            System.out.println("Select bench Pokémon (0-" + (bench.size()-1) + "): " + bench);
                            int index = scanner.nextInt();
                            scanner.nextLine();
                            if (index >= 0 && index < bench.size()) {
                                Pokemon temp = active;
                                active = bench.remove(index);
                                bench.add(temp);
                                active.setEnergyAttached(active.getEnergyAttached() - active.getRetreatCost());
                                System.out.println("Retreated to " + active.getName());
                                if (player1Turn) {
                                    activePlayer1 = active;
                                } else {
                                    activePlayer2 = active;
                                }
                            } else {
                                System.out.println("Invalid bench selection!");
                            }
                        } else {
                            System.out.println("Not enough energy or no bench Pokémon to retreat!");
                        }
                        break;

                    case 6:
                        attacked = true;
                        System.out.println("Turn passed");
                        break;

                    default:
                        System.out.println("Invalid action");
                }
                printGameState(player1Turn);
            } catch (Exception e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
        return checkGameEnd();
    }

    private void handleKnockout(ArrayList<Card> hand, ArrayList<Card> prizes, Pokemon knockedOut) {
        System.out.println(knockedOut.getName() + " was knocked out!");
        if (player1Turn) {
            discardPlayer2.add(knockedOut);
            for (int i = 0; i < knockedOut.getEnergyAttached(); i++) {
                discardPlayer2.add(new Energy("Generic"));
            }
            knockedOut.setEnergyAttached(0);
            if (!benchPlayer2.isEmpty()) {
                System.out.println("Player 2, select a Pokémon from your bench to become active (0-" + (benchPlayer2.size()-1) + "): " + benchPlayer2);
                int index = scanner.nextInt();
                scanner.nextLine();
                if (index >= 0 && index < benchPlayer2.size()) {
                    activePlayer2 = benchPlayer2.remove(index);
                    System.out.println("Player 2's new active Pokémon: " + activePlayer2.getName());
                } else {
                    System.out.println("Invalid selection! Choosing first Pokémon from bench.");
                    activePlayer2 = benchPlayer2.remove(0);
                    System.out.println("Player 2's new active Pokémon: " + activePlayer2.getName());
                }
            } else {
                activePlayer2 = null;
            }
        } else {
            discardPlayer1.add(knockedOut);
            for (int i = 0; i < knockedOut.getEnergyAttached(); i++) {
                discardPlayer1.add(new Energy("Generic"));
            }
            knockedOut.setEnergyAttached(0);
            if (!benchPlayer1.isEmpty()) {
                System.out.println("Player 1, select a Pokémon from your bench to become active (0-" + (benchPlayer1.size()-1) + "): " + benchPlayer1);
                int index = scanner.nextInt();
                scanner.nextLine();
                if (index >= 0 && index < benchPlayer1.size()) {
                    activePlayer1 = benchPlayer1.remove(index);
                    System.out.println("Player 1's new active Pokémon: " + activePlayer1.getName());
                } else {
                    System.out.println("Invalid selection! Choosing first Pokémon from bench.");
                    activePlayer1 = benchPlayer1.remove(0);
                    System.out.println("Player 1's new active Pokémon: " + activePlayer1.getName());
                }
            } else {
                activePlayer1 = null;
            }
        }

        if (!prizes.isEmpty()) {
            hand.add(prizes.remove(0));
            if (player1Turn) {
                prizeCountPlayer2--;
                System.out.println("Player 1 takes a prize card! " + prizeCountPlayer2 + " prizes remaining for Player 2");
            } else {
                prizeCountPlayer1--;
                System.out.println("Player 2 takes a prize card! " + prizeCountPlayer1 + " prizes remaining for Player 1");
            }
        }
    }

    private boolean checkGameEnd() {
        if (prizeCountPlayer1 == 0) {
            System.out.println("Player 1 wins by taking all prize cards!");
            return true;
        }
        if (prizeCountPlayer2 == 0) {
            System.out.println("Player 2 wins by taking all prize cards!");
            return true;
        }
        if (activePlayer1 == null && benchPlayer1.isEmpty()) {
            System.out.println("Player 2 wins - Player 1 has no Pokémon left!");
            return true;
        }
        if (activePlayer2 == null && benchPlayer2.isEmpty()) {
            System.out.println("Player 1 wins - Player 2 has no Pokémon left!");
            return true;
        }
        return false;
    }

    private void applyTrainerEffect(Trainer trainer, ArrayList<Card> deck, ArrayList<Card> hand, ArrayList<Card> returnDeck) {
        if (trainer.getName().equals("Bill")) {
            drawCard(deck, hand, 2);
            System.out.println("Bill's effect: Drew 2 cards!");
            System.out.println("Updated Hand: " + hand);
        } else if (trainer.getName().equals("Professor's Research")) {
            if (!hand.isEmpty()) {
                returnDeck.addAll(hand);
                Collections.shuffle(returnDeck);
                hand.clear();
            }
            drawCard(deck, hand, 7);
            System.out.println("Professor's Research effect: Returned hand to deck and drew 7 cards!");
            System.out.println("Updated Hand: " + hand);


        } else if (trainer.getName().equals("Hero")) {
            ArrayList<Pokemon> availablePokemon = new ArrayList<>();
            if (player1Turn) {
                if (activePlayer1 != null) availablePokemon.add(activePlayer1);
                availablePokemon.addAll(benchPlayer1);
            } else {
                if (activePlayer2 != null) availablePokemon.add(activePlayer2);
                availablePokemon.addAll(benchPlayer2);
            }
    
            if (availablePokemon.isEmpty()) {
                System.out.println("No Pokémon available to apply Hero effect!");
                return;
            }
    
            System.out.println("Select a Pokémon to increase HP by 100:");
            for (int i = 0; i < availablePokemon.size(); i++) {
                System.out.println(i + ": " + availablePokemon.get(i).getName() + 
                                 " (Current HP: " + availablePokemon.get(i).getHp() + ")");
            }
    
            int selection = scanner.nextInt();
            scanner.nextLine();
            
            if (selection >= 0 && selection < availablePokemon.size()) {
                Pokemon selected = availablePokemon.get(selection);
                selected.setHp(selected.getHp() + 100);
                System.out.println("Hero effect: Increased " + selected.getName() + "'s HP by 100!");
                System.out.println("New HP: " + selected.getHp());
            } else {
                System.out.println("Invalid selection! Hero effect not applied.");
            }
        } else if (trainer.getName().equals("Professor Elm's Advice")) {
            if (player1Turn) {
                ProfessorElmsAdviceActivePlayer1 = true;
                System.out.println("ProfessorElmsAdvice effect: Player 1's next attack will deal double damage!");
            } else {
                ProfessorElmsAdviceActivePlayer2 = true;
                System.out.println("ProfessorElmsAdvice effect: Player 2's next attack will deal double damage!");
            }
        }
    }

    public void run() {
        setupGame();
        while (!gameWon) {
            if (player1Turn) {
                gameWon = playerTurn(deckPlayer1, handPlayer1, prizesPlayer1, deckPlayer1, benchPlayer1,
                        activePlayer1, deckPlayer2, handPlayer2, prizesPlayer2, activePlayer2);
                player1Turn = false;
            } else {
                gameWon = playerTurn(deckPlayer2, handPlayer2, prizesPlayer2, deckPlayer2, benchPlayer2,
                        activePlayer2, deckPlayer1, handPlayer1, prizesPlayer1, activePlayer1);
                player1Turn = true;
            }
        }
        scanner.close();
    }
}