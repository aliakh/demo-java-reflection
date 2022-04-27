package demo.part1.arrays;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

public class ClassArrayTest {

    @Test
    public void accessPrimitiveArray() {
        int[] array = new int[]{2};
        assertEquals(1, Array.getLength(array));

        assertEquals(2, Array.getInt(array, 0));
        Array.setInt(array, 0, 3);
        assertEquals(3, Array.getInt(array, 0));
    }

    @Test
    public void accessObjectArray() {
        Integer[] array = new Integer[]{2};
        assertEquals(1, Array.getLength(array));

        assertEquals(2, Array.getInt(array, 0));
        Array.setInt(array, 0, 3);
        assertEquals(3, Array.getInt(array, 0));
    }

    @Test
    public void newInstanceWithLength() {
        Object array = Array.newInstance(int.class, 2);
        assertTrue(array.getClass().isArray());

        assertArrayEquals(new int[]{0, 0}, (int[]) array);
    }

    @Test
    public void newInstanceWithDimensions() {
        Object array = Array.newInstance(int.class, 1, 2, 3);
        assertTrue(array.getClass().isArray());

        assertArrayEquals(
            new int[][][]{
                new int[][]{
                    new int[]{0, 0, 0},
                    new int[]{0, 0, 0}
                },
            },
            (int[][][]) array
        );
    }
}
