class Trainer extends Card {
    private String name;
    private String ability;

    public Trainer(String name, String ability) {
        this.type = "Trainer";
        this.name = name;
        this.ability = ability;
    }

    public String getName() { return name; }
    public String getAbility() { return ability; }

    @Override
    public String toString() {
        return name + " (" + ability + ")";
    }
}
