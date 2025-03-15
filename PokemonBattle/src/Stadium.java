/**
 * Represents a Pokemon battle stadium where two Pokemon can fight
 * The Stadium manages the battle mechanics including turn order and victory conditions
 */
public class Stadium {

    /**
     * Conducts a battle between two Pokemon until one is knocked out
     * Turn order is determined by Pokemon speed stat (faster Pokemon attacks first)
     *
     * @param p1 First Pokemon (controlled by Player 1)
     * @param p2 Second Pokemon (controlled by Player 2)
     */
    public void battle(Pokemon p1, Pokemon p2) {
        // Battle continues until one Pokemon's HP reaches 0 or below
        while (p1.getHp() > 0 && p2.getHp() > 0) {
            // Determine turn order based on speed stat
            if (p1.getSpeed() > p2.getSpeed()) {
                // p1 is faster, so attacks first
                p1.attacks(p2);

                // Only let p2 attack if it's still conscious after p1's attack
                if (p2.getHp() > 0) {
                    p2.attacks(p1);
                }
            } else {
                // p2 is faster (or they have equal speed), so attacks first
                p2.attacks(p1);

                // Only let p1 attack if it's still conscious after p2's attack
                if (p1.getHp() > 0) {
                    p1.attacks(p2);
                }
            }
        }

        // Determine and announce the winner
        if (p1.getHp() > p2.getHp()) {
            System.out.println(" Player 1 wins the battle ");
        } else {
            System.out.println(" Player 2 wins the battle ");
        }

        // Debug line (commented out) to show final HP values
        //System.out.println(p1.getHp() + " " + p2.getHp());
    }

    /*
     * Battle sequence explanation:
     * 1. First, we check which Pokemon has higher Speed stat
     * 2. The faster Pokemon attacks first
     * 3. If the defending Pokemon survives (HP > 0), it counter-attacks
     * 4. This process repeats until one Pokemon's HP reaches 0
     * 5. The Pokemon with remaining HP wins the battle
     *
     * Notes:
     * - The attack damage calculation is handled by the Pokemon class's attacks() method
     * - This likely includes calculating the difference between attack and defense stats
     * - Type advantages and other battle mechanics would also be implemented there
     */
}