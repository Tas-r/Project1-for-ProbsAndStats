public class DoorSimulation {
    public static void main(String[] args) {

      


        PlayGame DoorGame = null;
        System.out.println("Win rate without switching: " + DoorGame.simulate(false, 1000000) + "%");
        System.out.println("Win rate with switching: " + DoorGame.simulate(true, 1000000) + "%");
    }
}


