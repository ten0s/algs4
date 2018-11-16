import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import java.util.Arrays;

public class BinarySearchTest {
    @Test
    public void indexOfEmptyArray() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {}, 0));
    }

    @Test
    public void indexOfNotFound() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {1,2,3}, 0));
    }

    @Test
    public void indexOfFound() {
        assertEquals(1, BinarySearch.indexOf(new int[] {1,2,3}, 2));
    }

    @Test
    public void dedupEmptyArray() {
        assertArrayEquals(new int[] {}, BinarySearch.dedup(new int[] {}));
    }

    @Test
    public void dedupNonEmptyArray() {
        assertArrayEquals(new int[] {1,2,3,4,5}, BinarySearch.dedup(new int[] {1,2,2,3,4,4,5}));
    }

    @Test
    public void rankEmptyArray() {
        assertEquals(0, BinarySearch.rank(new int[] {}, 0));
    }

    @Test
    public void rankNonEmptyArray() {
        assertEquals(2, BinarySearch.rank(new int[] {-2,-1,0,1,2}, 0));
    }

    @Test
    public void countEmptyArray() {
        assertEquals(0, BinarySearch.count(new int[] {}, 0));
    }

    @Test
    public void countNonEmptyArray() {
        assertEquals(1, BinarySearch.count(new int[] {-2,-1,0,1,2}, 0));
    }
}
