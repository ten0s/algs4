import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import java.util.Arrays;

public class StaticSETofIntsTest {
    @Test
    public void howManyEmptyArray() {
        assertEquals(0, new StaticSETofInts(new int[] {}).howMany(0));
    }

    @Test
    public void howManyNotFound() {
        assertEquals(0, new StaticSETofInts(new int[] {1,2,3}).howMany(0));
    }

    @Test
    public void howManySingle() {
        assertEquals(1, new StaticSETofInts(new int[] {1,2,3}).howMany(2));
    }

    @Test
    public void howManyMultiple() {
        assertEquals(3, new StaticSETofInts(new int[] {1,2,2,2,3}).howMany(2));
    }

    @Test
    public void howManyAll() {
        assertEquals(5, new StaticSETofInts(new int[] {2,2,2,2,2}).howMany(2));
    }
}
