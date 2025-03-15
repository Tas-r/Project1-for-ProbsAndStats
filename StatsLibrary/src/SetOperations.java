
import java.util.ArrayList;
import java.util.Arrays;

public class SetOperations {

    ArrayList<Integer> list1;
    ArrayList<Integer> list2;

    public SetOperations() { // Constructor must have ()
        list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        list2 = new ArrayList<>(Arrays.asList(5, 6, 7));
    }

    public ArrayList<Integer> SetUnion(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> union = new ArrayList<>(list1);
        for (Integer day : list2) {
            if (!union.contains(day)) {
                union.add(day);
            }


        }
        return union;
    }

    public ArrayList<Integer> SetIntersect(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> intersect = new ArrayList<>();
        for (Integer day : list1) {
            if (list2.contains(day)) {
                intersect.add(day);
            }
        }
        return intersect;


    }
}



