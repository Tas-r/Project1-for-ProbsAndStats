class Energy extends Card {
    private String energyType;

    public Energy(String energyType) {
        this.type = "Energy";
        this.energyType = energyType;
    }

    public String getEnergyType() { return energyType; }

    @Override
    public String toString() {
        return energyType + " Energy";
    }
}