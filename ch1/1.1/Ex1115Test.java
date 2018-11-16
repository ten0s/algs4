import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import java.util.Arrays;

public class Ex1115Test {
    @Test
    public void returnsArrayOfLengthM() {
        int m = 4;
        int[] r = Ex1115.histogram(new int [] {}, m);
        assertEquals(m, r.length);
    }

    @Test
    public void returnsHistogram() {
        int m = 4;
        int[] r = Ex1115.histogram(new int [] {1,2,2,3,3,3}, m);
        int[] p = new int[] {0,1,2,3};
        assertTrue(Arrays.equals(r, p));
    }

    @Test
    public void sumOfAllReturnValuesShouldBeLengthOfA() {
        int m = 4;
        // the values in a[] are all between 0 and m-1
        int[] r = Ex1115.histogram(new int [] {3,2,1,0}, 4);
        // each entry is exactly once
        int[] p = new int[] {1,1,1,1};
        int sum = Arrays.stream(r).sum();
        assertEquals(m, sum);
    }
}
