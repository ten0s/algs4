import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import java.util.Arrays;

public class BinarySearchTest {
    @Test
    public void smallestIndexOfEmptyArray() {
        assertEquals(-1, BinarySearch.smallestIndexOf(0, new int[] {}));
    }

    @Test
    public void smallestIndexOfNotFound() {
        assertEquals(-1, BinarySearch.smallestIndexOf(0, new int[] {1,2,3}));
    }

    @Test
    public void smallestIndexOfFoundSingle() {
        assertEquals(1, BinarySearch.smallestIndexOf(2, new int[] {1,2,3}));
    }

    @Test
    public void smallestIndexOfFoundMultiple() {
        assertEquals(1, BinarySearch.smallestIndexOf(2, new int[] {1,2,2,2,3}));
    }

    @Test
    public void smallestIndexOfFoundAll() {
        assertEquals(0, BinarySearch.smallestIndexOf(2, new int[] {2,2,2,2,2}));
    }

    @Test
    public void largestIndexOfEmptyArray() {
        assertEquals(-1, BinarySearch.largestIndexOf(0, new int[] {}));
    }

    @Test
    public void largestIndexOfNotFound() {
        assertEquals(-1, BinarySearch.largestIndexOf(0, new int[] {1,2,3}));
    }

    @Test
    public void largestIndexOfFoundSingle() {
        assertEquals(1, BinarySearch.largestIndexOf(2, new int[] {1,2,3}));
    }

    @Test
    public void largestIndexOfFoundMultiple() {
        assertEquals(3, BinarySearch.largestIndexOf(2, new int[] {1,2,2,2,3}));
    }

    @Test
    public void largestIndexOfFoundAll() {
        assertEquals(4, BinarySearch.largestIndexOf(2, new int[] {2,2,2,2,2}));
    }
}
