class Pokemon extends Card {
    private String name;
    private String pokeType;
    private int hp;
    private int energyAttached;
    private String weakness;
    private int retreatCost;

    public Pokemon(String name, int hp, String pokeType, int retreatCost, String weakness) {
        this.type = "Pokemon";
        this.name = name;
        this.hp = hp;
        this.pokeType = pokeType;
        this.energyAttached = 0;
        this.weakness = weakness;
        this.retreatCost = retreatCost;
    }
    // Getters and setters
    public String getName() { return name; }
    public String getPokeType() { return pokeType; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getEnergyAttached() { return energyAttached; }
    public void setEnergyAttached(int energy) { this.energyAttached = energy; }
    public String getWeakness() { return weakness; }
    public int getRetreatCost() { return retreatCost; }


    @Override
    public String toString() {
        return name + " (" + pokeType + ", HP: " + hp + ")";
    }
}

