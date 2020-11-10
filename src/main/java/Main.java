import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) {

        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

        arrayDeque.push(1);
        arrayDeque.push(2);
        arrayDeque.push(3);

        System.out.println(arrayDeque.peek());
    }
}
