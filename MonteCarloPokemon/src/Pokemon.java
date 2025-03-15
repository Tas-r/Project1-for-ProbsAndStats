class Pokemon extends Card{
    private int hp;
    private String attackName;
    private int attackDamage;
    private String weakness;

    Pokemon(String name, int hp, String attackName, int attackDamage,  String weakness) {
        this.cardName = name;
        this.hp = hp;
        this.attackName = attackName;
        this.attackDamage = attackDamage;
        this.weakness = weakness;
       }
}
