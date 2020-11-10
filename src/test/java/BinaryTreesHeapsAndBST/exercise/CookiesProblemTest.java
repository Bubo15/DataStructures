package BinaryTreesHeapsAndBST.exercise;

import BinaryTreesHeapsAndBST.exercise.implementations.CookiesProblem;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CookiesProblemTest {

    @Test
    public void testSolveHasSolution() {
        Integer result = new CookiesProblem().solve(7, new int[]{1, 2, 3, 9, 10, 12});
        assertNotNull(result);
        assertEquals(Integer.valueOf(2), result);
    }

    @Test
    public void testSolveHasNoSolution() {
        Integer result = new CookiesProblem().solve(10, new int[]{1, 1, 1, 1});
        assertNotNull(result);
        assertEquals(Integer.valueOf(-1), result);
    }
}