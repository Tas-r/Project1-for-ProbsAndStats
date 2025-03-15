/**
 * Represents a Pokemon character with battle stats
 * Contains attributes and methods to participate in battles
 */
public class Pokemon {
    private int hp;       // Hit points (health) of the Pokemon
    private int attack;   // Attack power - determines damage potential
    private int defense;  // Defense power - reduces damage taken
    private int speed;    // Speed - determines who attacks first in battle
    //global variable

    /**
     * Returns the current HP (health points) of the Pokemon
     * @return Current HP value
     */
    public int getHp() {
        return hp;
    }

    /**
     * Returns the attack stat of the Pokemon
     * @return Attack value
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns the defense stat of the Pokemon
     * @return Defense value
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Returns the speed stat of the Pokemon
     * @return Speed value
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the HP (health points) value for this Pokemon
     * @param userHP New HP value to assign
     */
    public void setHp(int userHP) {
        hp = userHP;
    }

    /**
     * Sets the attack stat for this Pokemon
     * @param userAttack New attack value to assign
     */
    public void setAttack(int userAttack) {
        attack = userAttack;
    }

    /**
     * Sets the defense stat for this Pokemon
     * @param userDefense New defense value to assign
     */
    public void setDefense(int userDefense) {
        defense = userDefense;
    }

    /**
     * Sets the speed stat for this Pokemon
     * @param userSpeed New speed value to assign
     */
    public void setSpeed(int userSpeed) {
        speed = userSpeed;
    }

    /**
     * Executes an attack against the specified target Pokemon
     * Damage is calculated as the difference between this Pokemon's attack
     * and the target Pokemon's defense
     *
     * @param pk2 The target Pokemon to attack
     */
    public void attacks(Pokemon pk2) {
        pk2.setHp(pk2.getHp()-(getAttack()-pk2.getDefense()));
    }
}