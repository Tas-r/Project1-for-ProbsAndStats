import java.util.ArrayList;
import java.util.Arrays;

public class testSetOperations {
    public static void main(String[] args) {
        SetOperations tester = new SetOperations();
        ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(3, 4, 5, 6));
        System.out.println("Our union result: " + tester.SetUnion(list1, list2));
        System.out.println("Our intersection result: " + tester.SetIntersect(list1, list2));
    }
}