package BinaryTreesHeapsAndBST.exercise.implementations;

import java.util.PriorityQueue;
import java.util.Queue;

public class CookiesProblem {

    public Integer solve(int requiredSweetness, int[] cookiesInput) {
        Queue<Integer> cookies = new PriorityQueue<>();

        for (int cookie : cookiesInput) {
            cookies.offer(cookie);
        }

        int minSweetness = cookies.peek();
        int steps = 0;

        while (minSweetness < requiredSweetness && cookies.size() > 1){
            int leastCookie = cookies.poll();
            int secondCookie = cookies.poll();

            int combination =  leastCookie + 2 * secondCookie;

            cookies.add(combination);

            minSweetness = cookies.peek();
            steps++;
        }

        return minSweetness > requiredSweetness ? steps : -1;
    }
}
