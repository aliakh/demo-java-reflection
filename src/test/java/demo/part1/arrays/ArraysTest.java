package demo.part1.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArraysTest {

    @Test
    public void determinePrimitiveArray() {
        Class<?> clazz = int[].class;
        assertTrue(clazz.isArray());

        assertEquals(int.class, clazz.getComponentType());
        assertEquals(int.class, clazz.componentType());

        assertEquals(int[][].class, clazz.arrayType());
    }

    @Test
    public void determineObjectArray() {
        Class<?> clazz = Integer[].class;
        assertTrue(clazz.isArray());

        assertEquals(Integer.class, clazz.getComponentType());
        assertEquals(Integer.class, clazz.componentType());

        assertEquals(Integer[][].class, clazz.arrayType());
    }
}
