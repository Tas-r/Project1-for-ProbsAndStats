import java.util.Random;

/**
 * Represents a door in the Monty Hall problem
 * Each door either contains the prize or doesn't
 */
class Door {
    boolean hasPrize; // True if this door has the prize behind it

    /**
     * Constructor for creating a door
     *
     * @param hasPrize Whether this door has the prize
     */
    Door(boolean hasPrize) {
        this.hasPrize = hasPrize;
    }
}