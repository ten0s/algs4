import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import java.util.Arrays;

public class LocalMinimumTest {
    @Test
    public void emptyArray() {
        assertEquals(-1, LocalMinimum.indexOf(new int[] {}));
    }

    @Test
    public void oneElement() {
        assertEquals(0, LocalMinimum.indexOf(new int[] {1}));
    }

    @Test
    public void twoDistinctElementsASC() {
        assertEquals(0, LocalMinimum.indexOf(new int[] {1,2}));
    }

    @Test
    public void twoDistinctElementsDES() {
        assertEquals(1, LocalMinimum.indexOf(new int[] {2,1}));
    }

    @Test
    public void threeDistinctElements() {
        assertEquals(1, LocalMinimum.indexOf(new int[] {2,1,3}));
    }

    @Test
    public void someDistinctElements() {
        assertEquals(0, LocalMinimum.indexOf(new int[] {4,5,6,9,8,10,12}));
    }

    @Test
    public void someDistinctElements2() {
        assertEquals(2, LocalMinimum.indexOf(new int[] {9,7,2,8,5,6,3,4}));
    }
}
