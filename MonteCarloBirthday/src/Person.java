import java.util.Random;


class Person {
    private final int birthday; // Represented as day of the year (1-365)


    public Person() {
        Random rand = new Random();
        this.birthday = rand.nextInt(365) + 1; // Random birthday between 1 and 365
    }

    public int getBirthday() {
        return birthday;
    }
}