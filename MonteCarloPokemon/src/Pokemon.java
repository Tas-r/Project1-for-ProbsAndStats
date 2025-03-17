class Pokemon extends Card{
    private int hp;
    private String attackName;
    private String weakness;

    Pokemon(String name, int hp, String attackName,  String weakness) {
        this.cardName = name;
        this.hp = hp;
        this.attackName = attackName;

        this.weakness = weakness;
       }
}
