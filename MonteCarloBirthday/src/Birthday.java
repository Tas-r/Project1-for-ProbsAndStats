
class Birthday {
    private int people;  // Number of people in the group
    private int trials;  // Number of simulation trials to run


    public Birthday(int people, int trials) {
        this.people = people;
        this.trials = trials;
    }

    public double runTrials() {
        int sharedBirthdays = 0;  // Counter for trials with at least one shared birthday

        // Run the specified number of trials
        for (int i = 0; i < trials; i++) {
            if (sameBirthday()) {
                sharedBirthdays++;  // increase when a shared birthday is found
            }
        }

        // Calculate and return the percentage
        return (double) sharedBirthdays / trials*100;
    }


    public boolean sameBirthday() {
        int[] birthdays = new int[365]; // Array to track birthday occurrences (days 1-365)

        // Assign random birthdays to each person in the group
        for (int i = 0; i < people; i++) {
            Person someoneInClass = new Person();  // Create a person with random birthday
            int birthday = someoneInClass.getBirthday();  // Get their birthday (1-365)

            if (birthdays[birthday - 1] > 0) { // Check if this birthday was already assigned
                return true;  // Found a shared birthday
            }
            birthdays[birthday - 1]++;  // Mark this birthday as used
        }
        return false;  // No shared birthdays found
    }
}