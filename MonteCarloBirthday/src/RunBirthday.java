public class RunBirthday {
    public static void main(String[] args) {


        double probability = new Birthday(33, 10000).runTrials();
        System.out.println("Probability two people in class share a birthday: " + probability + "%");
    }
}